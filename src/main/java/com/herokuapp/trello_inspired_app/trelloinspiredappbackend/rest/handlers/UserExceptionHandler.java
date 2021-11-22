package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BaseExceptionHandler;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserAlreadyHasAdminException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserIsNotMemberOfBoardException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {

    public UserExceptionHandler(MessageSource messageSource) {
        super(messageSource);
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

    @ExceptionHandler (UserNotFoundException.class)
    ResponseEntity<ExceptionDto> onUserNotFoundException(UserNotFoundException exception,
                                                         Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

}
