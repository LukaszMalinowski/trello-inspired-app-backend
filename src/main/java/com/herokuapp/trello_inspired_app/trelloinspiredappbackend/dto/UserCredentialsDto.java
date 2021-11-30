package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserCredentialsDto {

    @NotBlank
    private String username;

    @Size(min = 8, max = 128)
    @NotBlank (message = "Password is mandatory")
    private String password;

}
