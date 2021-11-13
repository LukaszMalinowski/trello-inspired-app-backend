package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(Long boardId) {
        super("Board with id " + boardId + " not found");
    }
}
