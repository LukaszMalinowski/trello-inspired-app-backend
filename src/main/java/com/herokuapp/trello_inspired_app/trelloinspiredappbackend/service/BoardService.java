package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardColumnDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardMembersDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.*;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.BoardMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.*;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardUserRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO: think about if this is needed - if board has team - check if users is a member of a team
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;

    private final BoardMapper boardMapper;

    public List<BoardDto> getAllBoards() {
        log.info("Getting all boards");
        var boards = boardRepository.findAll();
        return boards.stream()
                .filter(board -> board.getTeam() == null)
                .map(boardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addNewBoard(BoardDto newBoard, Long userId) {
        log.info("Adding new board");

        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Board board = Board.builder()
                .name(newBoard.getName())
                .description(newBoard.getDescription())
                .createdDate(LocalDateTime.now())
                .owner(user)
                .build();

        board.setColumns(createDefaultColumns(board));

        var boardId = boardRepository.save(board).getBoardId();
        addMember(user.getUserId(), boardId, ADMIN);
        return boardId;
    }

    private List<Column> createDefaultColumns(
            Board board) {
        List<Column> defaultColumns = new ArrayList<>();
        defaultColumns.add(new Column("Todo", board));
        defaultColumns.add(new Column("In progress", board));
        defaultColumns.add(new Column("Done", board));
        return defaultColumns;
    }

    @Transactional
    public void deleteBoard(Long boardId, User user) {
        log.info("Deleting board with id {}", boardId);

        verifyIfUserIsAdmin(boardId, user.getUserId());

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        boardRepository.delete(board);
    }

    public List<BoardUserDto> getAllBoardMembers(Long boardId) {
        log.info("Getting all members");
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return board.getMembers().stream()
                .map(boardMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAdminPrivileges(Long boardId, Long userId, User user) {
        log.info("Giving user {} admin privileges", userId);

        verifyIfUserIsAdmin(boardId, user.getUserId());

        BoardUser boardUser = boardUserRepository
                .findBoardUserByBoard_BoardIdAndAndUser_UserId(boardId, userId)
                .orElseThrow(UserIsNotMemberOfBoardException::new);

        if (boardUser.getRole() == ADMIN) {
            throw new UserAlreadyHasAdminException();
        }

        boardUser.setRole(ADMIN);
        boardUserRepository.save(boardUser);
    }

    public BoardColumnDto getBoardDetails(Long boardId) {
        log.info("Getting board details for board with id {}", boardId);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        if (board.getTeam() != null) {
            verifyIfUserIsTeamMember(board.getTeam());
        }

        return boardMapper.toColumnDto(board);
    }

    private void verifyIfUserIsTeamMember(Team team) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getCredentials().equals("")) {
            throw new UserNotAuthenticatedException();
        }

        if (team.getMembers().stream()
                .noneMatch(teamUser -> teamUser.getUser().getUsername().equals(authentication.getName()))) {
            throw new UserNotPermittedException();
        }
    }

    public boolean isNotBoardMember(Long boardId, Long userId) {
        return !boardUserRepository.existsByBoard_BoardIdAndAndUser_UserId(boardId, userId);
    }

    public void addMember(Long userId, Long boardId, Role role) {
        log.info("Adding user {} to board {}", userId, boardId);
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        var boardUser = BoardUser.builder()
                .user(user)
                .board(board)
                .joinDate(LocalDateTime.now())
                .role(role)
                .build();
        boardUserRepository.save(boardUser);
    }

    public List<BoardDto> getAllBoardsThatUserIsAssignedTo(Long userId, User user) {
        log.info("Getting all boards for user {}", userId);
        verifyUser(userId, user);
        return boardUserRepository.findBoardUsersByUser_UserId(userId).stream()
                .map(BoardUser::getBoard)
                .filter(board -> board.getTeam() == null)
                .map(boardMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BoardMembersDto> getAllBoardsWithMembersWhichUserHasAdminRole(Long userId, User user) {
        log.info("Getting all boards with members for user {}", userId);
        verifyUser(userId, user);
        return boardUserRepository.findBoardUsersByUser_UserId_AndRole(userId, ADMIN).stream()
                .map(BoardUser::getBoard)
                .filter(board -> board.getTeam() == null)
                .map(boardMapper::toMembersDto)
                .collect(Collectors.toList());
    }

    private void verifyUser(Long userId, User user) {
        if (!user.getUserId().equals(userId)) {
            throw new UserNotPermittedException();
        }
    }

    private void verifyIfUserIsAdmin(Long boardId, Long userId) {
        BoardUser boardUser = boardUserRepository.findBoardUserByBoard_BoardIdAndAndUser_UserId(boardId, userId)
                .orElseThrow(UserIsNotMemberOfBoardException::new);
        if (boardUser.getRole() != ADMIN) {
            throw new UserIsNotAdminException();
        }
    }
}
