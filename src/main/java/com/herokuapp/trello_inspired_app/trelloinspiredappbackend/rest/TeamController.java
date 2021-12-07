package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TeamDetailsDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TeamDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotPermittedException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDetailsDto>> getAllTeams() {
        var teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    public ResponseEntity<TeamDto> addNewTeam(@RequestBody @Validated TeamDto teamDto, Principal principal) {
        var team = teamService.addNewTeam(teamDto, principal.getName());
        return ResponseEntity.ok(team);
    }

    @GetMapping("/{teamId}/boards")
    public ResponseEntity<List<BoardDto>> getAllTeamBoards(@PathVariable Long teamId, Principal principal) {
        var boards = teamService.getAllTeamBoards(teamId, principal.getName());
        return ResponseEntity.ok(boards);
    }

    @PostMapping("/{teamId}/boards")
    public ResponseEntity<BoardDto> addNewBoardInTeam(@PathVariable Long teamId, @RequestBody @Validated BoardDto boardDto, Principal principal) {
        var board = teamService.addTeamBoard(teamId, boardDto, principal.getName());
        return ResponseEntity.ok(board);
    }

    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> joinTeam(@PathVariable Long teamId, @PathVariable Long userId, Authentication authentication) {
        //TODO: extract it to some method in userService maybe
        var user = (User) authentication.getPrincipal();
        if (!user.getUserId().equals(userId)) {
            throw new UserNotPermittedException();
        }

        teamService.addTeamMember(userId, teamId);
        return ResponseEntity.noContent().build();
    }

}
