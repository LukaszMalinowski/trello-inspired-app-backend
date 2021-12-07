package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TeamUserDto {

    private LocalDateTime joinDate;
    private Long userId;
    private String username;
    private String name;
    private String surname;

}
