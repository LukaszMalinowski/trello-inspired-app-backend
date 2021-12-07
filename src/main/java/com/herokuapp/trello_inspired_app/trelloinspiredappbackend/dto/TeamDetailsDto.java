package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TeamDetailsDto {

    private Long teamId;
    private String name;
    private LocalDateTime createdDate;
    private UserDto owner;
    private Integer membersCount;

}
