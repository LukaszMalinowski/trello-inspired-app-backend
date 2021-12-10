package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.column.ColumnDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardColumnDto {

    private Long boardId;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private List<ColumnDto> columns;

}
