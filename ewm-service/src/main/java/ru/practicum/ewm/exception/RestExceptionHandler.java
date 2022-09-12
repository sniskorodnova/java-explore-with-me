package ru.practicum.ewm.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<Object> handleNoAuthException(NoHeaderException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("No auth for request");
        apiError.setReason(ex.getLocalizedMessage());
    //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
