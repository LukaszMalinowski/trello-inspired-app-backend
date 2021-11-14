package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.controller;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardUserRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @PostMapping
    public ResponseEntity<Void> addNewBoard(@RequestBody BoardDto boardDto) {
        //TODO: figure out if id needs to be returned
        boardService.addNewBoard(boardDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/{boardId}/users")
    public ResponseEntity<List<BoardUserDto>> getAllBoardMembers(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getAllBoardMembers(boardId));
    }

    @PostMapping ("/{boardId}/users/{userId}/admin")
    public ResponseEntity<Void> addAdminPrivileges(@PathVariable Long boardId, @PathVariable Long userId) {
        boardService.addAdminPrivileges(boardId, userId);
        return ResponseEntity.noContent().build();
    }

}
