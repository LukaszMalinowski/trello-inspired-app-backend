package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BaseExceptionHandler;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BoardNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class BoardExceptionHandler extends BaseExceptionHandler {

    public BoardExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    ResponseEntity<ExceptionDto> onBoardNotFoundException(BoardNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

}
