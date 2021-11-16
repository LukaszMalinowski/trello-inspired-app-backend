package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long taskId;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private UserDto owner;
    private UserDto assignee;

}
