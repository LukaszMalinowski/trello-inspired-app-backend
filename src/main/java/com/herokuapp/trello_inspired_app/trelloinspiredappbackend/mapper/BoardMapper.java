package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardColumnDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardMembersDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.board.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Board;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.BoardUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardDto toDto(Board board);

    BoardColumnDto toColumnDto(Board board);

    default BoardUserDto toUserDto(BoardUser boardUser) {
        return BoardUserDto.builder()
                .userId(boardUser.getUser().getUserId())
                .username(boardUser.getUser().getUsername())
                .name(boardUser.getUser().getName())
                .surname(boardUser.getUser().getSurname())
                .joinDate(boardUser.getJoinDate())
                .role(boardUser.getRole())
                .build();
    }

    BoardMembersDto toMembersDto(Board board);

}
