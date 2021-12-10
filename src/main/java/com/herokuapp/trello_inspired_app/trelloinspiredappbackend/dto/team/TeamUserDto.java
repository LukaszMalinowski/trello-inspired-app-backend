package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team;

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
