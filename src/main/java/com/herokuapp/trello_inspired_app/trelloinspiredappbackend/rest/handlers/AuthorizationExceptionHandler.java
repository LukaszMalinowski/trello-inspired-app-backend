package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class AuthorizationExceptionHandler extends BaseExceptionHandler {

    public AuthorizationExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionDto> onBadCredentialsException(BadCredentialsException exception, Locale locale) {
        return createResponse(exception, UNAUTHORIZED, locale);
    }

    @ExceptionHandler(UsernameIsTakenException.class)
    ResponseEntity<ExceptionDto> onUsernameIsTakenException(UsernameIsTakenException exception, Locale locale) {
        return createResponse(exception, CONFLICT, locale);
    }

    @ExceptionHandler(UserIsNotAdminException.class)
    ResponseEntity<ExceptionDto> onUserIsNotAdminException(UserIsNotAdminException exception, Locale locale) {
        return createResponse(exception, FORBIDDEN, locale);
    }

    @ExceptionHandler(UserNotPermittedException.class)
    ResponseEntity<ExceptionDto> onUserNotPermittedException(UserNotPermittedException exception, Locale locale) {
        return createResponse(exception, FORBIDDEN, locale);
    }

    @ExceptionHandler(UserIsNotMemberOfTeamException.class)
    ResponseEntity<ExceptionDto> onUserIsNotMemberOfTeamException(UserIsNotMemberOfTeamException exception,
                                                                  Locale locale) {
        return createResponse(exception, FORBIDDEN, locale);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    ResponseEntity<ExceptionDto> onUserNotAuthenticatedException(UserNotAuthenticatedException exception,
                                                                 Locale locale) {
        return createResponse(exception, UNAUTHORIZED, locale);
    }

}
