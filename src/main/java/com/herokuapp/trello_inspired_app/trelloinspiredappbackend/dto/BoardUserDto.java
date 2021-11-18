package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class BoardUserDto {

    Long userId;
    String username;
    String name;
    String surname;
    LocalDateTime joinDate;
    Role role;

}
