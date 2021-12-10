package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TeamDetailsDto {

    private Long teamId;
    private String name;
    private LocalDateTime createdDate;
    private UserDto owner;
    private List<TeamUserDto> members;

}
