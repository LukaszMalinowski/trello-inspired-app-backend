package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;

@Data
public class NewTaskDto {

    private String title;
    private String description;
    private Long ownerId;

}
