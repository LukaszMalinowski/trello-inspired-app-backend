package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class BoardMembersDto {

    long boardId;
    String name;
    String description;
    LocalDateTime createdDate;
    List<BoardUserDto> members;

}
