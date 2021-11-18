package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest.handlers;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ValidationExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BaseExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

    public GlobalExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    //TODO: figure out how to fix it
//    @ExceptionHandler (Exception.class)
//    protected ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
//        exception.printStackTrace();
//        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
//    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionDto> onValidationException(MethodArgumentNotValidException exception,
                                                                 Locale locale) {
        var validationErrors = getValidationErrors(exception);
        String description = getDescription(exception, locale);

        return ResponseEntity.badRequest().body(new ValidationExceptionDto(description, validationErrors));
    }

    private Map<String, String> getValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                 .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));
        return validationErrors;
    }

}
