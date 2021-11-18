package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

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

}
