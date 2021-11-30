package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TokenDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    @Value("${authentication.jwt.secret}")
    private String secret;

    public TokenDto createToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        Date expiresDate = calendar.getTime();


        var jwt = JWT.create()
                .withClaim(ID, user.getUserId())
                .withClaim(USERNAME, user.getUsername())
                .withClaim(NAME, user.getName())
                .withClaim(SURNAME, user.getSurname())
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC512(secret));
        return new TokenDto(jwt);
    }
}
