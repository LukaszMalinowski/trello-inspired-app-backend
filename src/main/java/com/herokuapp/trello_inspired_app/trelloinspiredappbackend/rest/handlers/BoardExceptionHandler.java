package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BoardNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.GlobalExceptionHandler;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserAlreadyHasAdminException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserIsNotMemberOfBoardException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class BoardExceptionHandler extends GlobalExceptionHandler {

    public BoardExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler (BoardNotFoundException.class)
    ResponseEntity<ExceptionDto> onBoardNotFoundException(BoardNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    @ExceptionHandler (UserAlreadyHasAdminException.class)
    ResponseEntity<ExceptionDto> onUserAlreadyHasAdminException(UserAlreadyHasAdminException exception, Locale locale) {
        return createResponse(exception, CONFLICT, locale);
    }

    @ExceptionHandler (UserIsNotMemberOfBoardException.class)
    ResponseEntity<ExceptionDto> onUserIsNotMemberOfBoardException(UserIsNotMemberOfBoardException exception,
                                                                   Locale locale) {
        return createResponse(exception, BAD_REQUEST, locale);
    }

}
