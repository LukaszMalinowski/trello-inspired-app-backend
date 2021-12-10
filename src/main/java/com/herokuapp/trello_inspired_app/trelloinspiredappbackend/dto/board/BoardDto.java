package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {

    private long boardId;
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private LocalDateTime createdDate;

}
