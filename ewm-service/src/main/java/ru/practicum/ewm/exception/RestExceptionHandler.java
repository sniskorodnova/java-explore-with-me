package ru.practicum.ewm.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    protected ApiError handleUserNotAllowedToViewEvent(UserNotAllowedToViewEventException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("User can't view this event");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleEventCantBeModified(EventCantBeModifiedException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Event can't be modified");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(ErrorStatus._409_CONFLICT.code, ex);
        apiError.setMessage("Integrity constraint has been violated");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiError apiError = new ApiError(ErrorStatus._409_CONFLICT.code, ex);
        apiError.setMessage("Value is invalid");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Incorrect field value");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleCategoryCantBeDeleted(CategoryCantBeDeleted ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Category can't be deleted");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleCategoryNotFound(CategoryNotFoundException ex) {
        ApiError apiError = new ApiError(ErrorStatus._404_NOT_FOUND.code, ex);
        apiError.setMessage("Category can't be viewed");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleIllegalEventState(IllegalEventStateException ex) {
        ApiError apiError = new ApiError(ErrorStatus._404_NOT_FOUND.code, ex);
        apiError.setMessage("Event can't be modified due to its status");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleEventCantBeCreated(EventCantBeCreatedException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Event can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleUserNotFound(UserNotFoundException ex) {
        ApiError apiError = new ApiError(ErrorStatus._404_NOT_FOUND.code, ex);
        apiError.setMessage("No such user");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleEventNotFound(EventNotFoundException ex) {
        ApiError apiError = new ApiError(ErrorStatus._404_NOT_FOUND.code, ex);
        apiError.setMessage("No such event");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleUserAlreadyHasRequest(UserAlreadyHasRequestException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Request can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleUserCantCreateRequest(UserCantCreateRequestException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Request can't be created");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleRequestNotFound(RequestNotFoundException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Request can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleRequestCantBeUpdated(RequestCantBeUpdatedException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Request can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleCompilationNotFound(CompilationNotFoundException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Compilation can't be updated");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }

    @ExceptionHandler
    protected ApiError handleNoHeader(MissingRequestHeaderException ex) {
        ApiError apiError = new ApiError(ErrorStatus._400_BAD_REQUEST.code, ex);
        apiError.setMessage("Request can't be processed");
        apiError.setReason(ex.getLocalizedMessage());
        if (Arrays.stream(ex.getStackTrace()).findFirst().isPresent()) {
            apiError.setErrors(Arrays.stream(ex.getStackTrace()).findFirst().get().toString());
        } else {
            apiError.setErrors(null);
        }
        return apiError;
    }
}
