package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.BoardUser;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardUserDto {

    private Long userId;
    private String username;
    private String name;
    private String surname;
    private LocalDateTime joinDate;
    private Role role;

    public BoardUserDto(BoardUser boardUser) {
        this.userId = boardUser.getUser().getUserId();
        this.username = boardUser.getUser().getUsername();
        this.name = boardUser.getUser().getName();
        this.surname = boardUser.getUser().getSurname();
        this.joinDate = boardUser.getJoinDate();
        this.role = boardUser.getRole();
    }

}
