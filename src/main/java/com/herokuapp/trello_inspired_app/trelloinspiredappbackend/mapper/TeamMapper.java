package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team.TeamDetailsDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team.TeamDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team.TeamUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Team;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.TeamUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto toDto(Team team);

    TeamDetailsDto toDetailsDto(Team team);

    default TeamUserDto toMembersDto(TeamUser teamUser) {
        return TeamUserDto.builder()
                .userId(teamUser.getUser().getUserId())
                .username(teamUser.getUser().getUsername())
                .name(teamUser.getUser().getName())
                .surname(teamUser.getUser().getSurname())
                .joinDate(teamUser.getJoinDate())
                .build();
    }
}
