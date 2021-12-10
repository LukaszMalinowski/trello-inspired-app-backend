package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.task;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewTaskDto {

    @NotNull
    @NotBlank
    private String title;
    private String description;

}
