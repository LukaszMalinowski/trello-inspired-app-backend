package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.column;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.task.TaskDto;
import lombok.Data;

import java.util.List;

@Data
public class ColumnDto {

    private Long columnId;
    private String name;
    private List<TaskDto> tasks;

}
