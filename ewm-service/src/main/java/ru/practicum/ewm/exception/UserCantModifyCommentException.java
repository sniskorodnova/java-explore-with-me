package ru.practicum.ewm.exception;

public class UserCantModifyCommentException extends RuntimeException {
    public UserCantModifyCommentException(String message) {
        super(message);
    }
}
