package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardMembersDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final BoardService boardService;

    @GetMapping("/{userId}/boards")
    public ResponseEntity<List<BoardDto>> getAllBoardsThatUserIsAssignedTo(@PathVariable Long userId, Authentication authentication) {
        var boards = boardService.getAllBoardsThatUserIsAssignedTo(userId, (User) authentication.getPrincipal());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{userId}/boards/admin")
    public ResponseEntity<List<BoardMembersDto>> getAllBoardsWhichUserHasAdminRole(@PathVariable Long userId, Authentication authentication) {
        var boards = boardService.getAllBoardsWithMembersWhichUserHasAdminRole(userId, (User) authentication.getPrincipal());
        return ResponseEntity.ok(boards);
    }

}
