package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ExceptionDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.ValidationExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler (Exception.class)
    protected ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
        exception.printStackTrace();
        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionDto> onValidationException(MethodArgumentNotValidException exception,
                                                                 Locale locale) {
        var validationErrors = getValidationErrors(exception);
        String description = getDescription(exception, locale);

        return ResponseEntity.badRequest().body(new ValidationExceptionDto(description, validationErrors));
    }

    protected ResponseEntity<ExceptionDto> createResponse(Exception exception, HttpStatus status, Locale locale) {
        String description = getDescription(exception, locale);
        return ResponseEntity.status(status).body(new ExceptionDto(description));
    }

    private Map<String, String> getValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                 .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));
        return validationErrors;
    }

    private String getDescription(Exception exception, Locale locale) {
        var key = exception.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(key, new Object[]{"test"}, locale);
        }
        catch (NoSuchMessageException ex) {
            description = key;
        }
        return description;
    }

}
