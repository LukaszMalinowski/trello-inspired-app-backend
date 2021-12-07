package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TeamDetailsDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TeamDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.UserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto toDto(Team team);

    default TeamDetailsDto toDetailsDto(Team team) {
        var teamOwner = team.getOwner();
        var owner = UserDto.builder()
                .userId(teamOwner.getUserId())
                .username(teamOwner.getUsername())
                .name(teamOwner.getName())
                .surname(teamOwner.getSurname())
                .surname(teamOwner.getSurname())
                .build();

        return TeamDetailsDto.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .createdDate(team.getCreatedDate())
                .owner(owner)
                .membersCount(team.getMembers().size())
                .build();
    }

}
