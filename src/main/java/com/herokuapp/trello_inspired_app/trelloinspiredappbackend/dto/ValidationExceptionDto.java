package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode (callSuper = true)
@Data
public class ValidationExceptionDto extends ExceptionDto {

    private Map<String, String> validationErrors;

    public ValidationExceptionDto(String description, Map<String, String> validationErrors) {
        super(description);
        this.validationErrors = validationErrors;
    }

}
