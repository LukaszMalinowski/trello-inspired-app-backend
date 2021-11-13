package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
@Slf4j
public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(Long boardId) {
        super("Board with id " + boardId + " not found");
        log.warn("Board with id {} not found", boardId);
    }
}
