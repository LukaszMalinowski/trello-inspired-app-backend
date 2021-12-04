package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardColumnDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Long> addNewBoard(@RequestBody BoardDto boardDto) {
        Long boardId = boardService.addNewBoard(boardDto);
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
    public ResponseEntity<Void> addAdminPrivileges(@PathVariable Long boardId, @PathVariable Long userId) {
        boardService.addAdminPrivileges(boardId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardColumnDto> getBoardDetails(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoardDetails(boardId));
    }

}
