package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardColumnDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    //TODO: change BoardDto here
    @PostMapping
    public ResponseEntity<Long> addNewBoard(@RequestBody @Validated BoardDto boardDto, Authentication authentication) {
        Long boardId = boardService.addNewBoard(boardDto, ((User) authentication.getPrincipal()).getUserId());
        return ResponseEntity.ok(boardId);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId, Authentication authentication) {
        boardService.deleteBoard(boardId, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}/users")
    public ResponseEntity<List<BoardUserDto>> getAllBoardMembers(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getAllBoardMembers(boardId));
    }

    @PostMapping("/{boardId}/users/{userId}/admin")
    public ResponseEntity<Void> addAdminPrivileges(@PathVariable Long boardId, @PathVariable Long userId, Authentication authentication) {
        boardService.addAdminPrivileges(boardId, userId, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardColumnDto> getBoardDetails(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoardDetails(boardId));
    }

}
