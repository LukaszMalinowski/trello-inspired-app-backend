package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String name;
    private String surname;

}
