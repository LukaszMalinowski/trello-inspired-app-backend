package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTaskDto {

    @NotBlank
    @NotNull
    private String title;
    private String description;
    private Long assigneeId;

}
