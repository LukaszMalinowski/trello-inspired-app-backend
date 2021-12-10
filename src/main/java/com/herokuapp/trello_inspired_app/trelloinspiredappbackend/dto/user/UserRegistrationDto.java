package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {

    @NotBlank(message = "Username is mandatory")
    private String username;
    @Size(min = 8, max = 128, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "First name is mandatory")
    private String name;
    @NotBlank(message = "Last name is mandatory")
    private String surname;

}
