package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
