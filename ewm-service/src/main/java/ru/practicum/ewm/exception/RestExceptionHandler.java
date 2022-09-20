package ru.practicum.ewm.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler
    protected ResponseEntity<Object> handleUserNotAllowedToViewEvent(UserNotAllowedToViewEventException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("User can't view this event");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleEventCantBeModified(EventCantBeModifiedException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Event can't be modified");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(CONFLICT, ex);
        apiError.setMessage("Integrity constraint has been violated");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiError apiError = new ApiError(CONFLICT, ex);
        apiError.setMessage("Value is invalid");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Incorrect field value");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleCategoryCantBeDeleted(CategoryCantBeDeleted ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Category can't be deleted");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleCategoryNotFound(CategoryNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage("Category can't be viewed");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleIllegalEventState(IllegalEventStateException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage("Event can't be modified due to its status");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleEventCantBeCreated(EventCantBeCreatedException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Event can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage("No such user");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleEventNotFound(EventNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage("No such event");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleUserAlreadyHasRequest(UserAlreadyHasRequestException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Request can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleUserCantCreateRequest(UserCantCreateRequestException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Request can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleRequestNotFound(RequestNotFoundException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Request can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleRequestCantBeUpdated(RequestCantBeUpdatedException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Request can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleCompilationNotFound(CompilationNotFoundException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        apiError.setMessage("Compilation can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        //    apiError.setErrors(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
