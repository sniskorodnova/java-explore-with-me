package ru.practicum.ewm.exception;

public class UserCantCreateCommentException extends RuntimeException {
    public UserCantCreateCommentException(String message) {
        super(message);
    }
}
