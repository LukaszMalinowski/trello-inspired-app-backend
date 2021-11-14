package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
@Slf4j
public class UserIsNotMemberOfBoardException extends RuntimeException {

    public UserIsNotMemberOfBoardException(Long boardId, Long userId) {
        super("User with id: " + userId + " is not member of board with id: " + boardId);
        log.warn("User with id: {} is not member of board with id: {}", userId, boardId);
    }

}
