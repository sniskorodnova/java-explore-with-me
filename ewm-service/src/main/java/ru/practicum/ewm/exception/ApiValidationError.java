package ru.practicum.ewm.exception;

import lombok.Data;

@Data
public class ApiValidationError {
    private String field;
    private Object fieldValue;

    public ApiValidationError(String field, Object fieldValue) {
        this.field = field;
        this.fieldValue = fieldValue;
    }
}
