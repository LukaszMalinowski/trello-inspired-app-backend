package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team.TeamDetailsDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team.TeamDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BoardNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.TeamNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserIsNotMemberOfTeamException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.BoardMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.TeamMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Team;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.TeamUser;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TeamRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TeamUserRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    private final BoardService boardService;

    private final TeamMapper teamMapper;
    private final BoardMapper boardMapper;

    @Transactional
    public TeamDetailsDto addNewTeam(TeamDto teamDto, String username) {
        log.info("Adding new team");

        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        var team = Team.builder()
                .name(teamDto.getName())
                .createdDate(LocalDateTime.now())
                .owner(user)
                .build();

        team = teamRepository.save(team);
        var teamUser = addTeamMember(user.getUserId(), team.getTeamId());
        team.setMembers(List.of(teamUser));
        return teamMapper.toDetailsDto(team);
    }

    @Transactional
    public TeamUser addTeamMember(Long userId, Long teamId) {
        log.info("Adding user {} to team {}", userId, teamId);
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        var teamUser = TeamUser.builder()
                .user(user)
                .team(team)
                .joinDate(LocalDateTime.now())
                .build();
        teamUserRepository.save(teamUser);
        addUserToAllBoards(userId, team);
        return teamUser;
    }

    private void addUserToAllBoards(Long userId, Team team) {
        if (team.getBoards() != null) {
            team.getBoards()
                    .stream()
                    .filter(board -> board.getMembers().stream()
                            .anyMatch(boardUser -> !boardUser.getUser().getUserId().equals(userId)))
                    .forEach(board -> boardService.addMember(userId, board.getBoardId(), ADMIN));
        }
    }

    @Transactional
    public BoardDto addTeamBoard(Long teamId, BoardDto boardDto, String username) {
        log.info("Adding new team board");
        var team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        verifyIfUserIsMemberOfTeam(team, user);

        var boardId = boardService.addNewBoard(boardDto, user.getUserId());
        var board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        board.setTeam(team);
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    private void verifyIfUserIsMemberOfTeam(Team team, User user) {
        var userIsMember = team.getMembers().stream()
                .anyMatch(teamUser -> teamUser.getUser().getUserId().equals(user.getUserId()));

        if (!userIsMember) {
            throw new UserIsNotMemberOfTeamException();
        }
    }

    public List<TeamDetailsDto> getAllTeams() {
        log.info("Getting all teams");
        var teams = teamRepository.findAll();
        return teams.stream()
                .map(teamMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    public List<BoardDto> getAllTeamBoards(Long teamId, String name) {
        log.info("Getting all boards for team {}", teamId);
        var user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        var team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        verifyIfUserIsMemberOfTeam(team, user);

        return team.getBoards().stream()
                .map(boardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTeam(Long teamId, String username) {
        log.info("Deleting team");
        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        var team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        verifyIfUserIsMemberOfTeam(team, user);

        teamRepository.delete(team);
    }
}
