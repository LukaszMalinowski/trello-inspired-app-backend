package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.team;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TeamDto {

    private Long teamId;
    @NotBlank
    private String name;

}
