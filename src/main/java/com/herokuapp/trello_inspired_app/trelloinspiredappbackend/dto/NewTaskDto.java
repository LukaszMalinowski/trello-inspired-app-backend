package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;

@Data
public class NewTaskDto {

    private String title;
    private String description;
    //TODO: figure out if it should be here or taken from jwt
    private Long ownerId;

}
