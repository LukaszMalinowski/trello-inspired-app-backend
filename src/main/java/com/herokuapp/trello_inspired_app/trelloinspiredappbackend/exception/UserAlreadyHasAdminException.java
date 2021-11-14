package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.CONFLICT)
@Slf4j
public class UserAlreadyHasAdminException extends RuntimeException {

    public UserAlreadyHasAdminException(Long userId) {
        super("User with id " + userId + " already has admin privileges");
        log.warn("Trying to add admin to user {} who already has this privileges", userId);
    }

}
