package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private long boardId;
    private String name;
    private String description;
    private LocalDateTime createdDate;

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.createdDate = board.getCreatedDate();
    }
}
