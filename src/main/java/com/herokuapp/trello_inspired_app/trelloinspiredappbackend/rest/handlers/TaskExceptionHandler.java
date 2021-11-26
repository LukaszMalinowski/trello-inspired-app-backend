package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BaseExceptionHandler;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.TaskNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class TaskExceptionHandler extends BaseExceptionHandler {

    public TaskExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler (TaskNotFoundException.class)
    ResponseEntity<ExceptionDto> onTaskNotFoundException(TaskNotFoundException exception,
                                                         Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

}
