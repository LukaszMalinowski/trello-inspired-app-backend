package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TokenDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.user.UserCredentialsDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.user.UserRegistrationDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.UserService;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated UserCredentialsDto userCredentials) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(jwtUtil.createToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        userService.registerUser(userRegistrationDto);
        return login(new UserCredentialsDto(userRegistrationDto.getUsername(), userRegistrationDto.getPassword()));
    }

}
