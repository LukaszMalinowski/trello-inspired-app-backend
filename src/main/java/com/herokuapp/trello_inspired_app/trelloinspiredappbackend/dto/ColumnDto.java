package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ColumnDto {

    private Long columnId;
    private String name;
    private List<TaskDto> tasks;

}
