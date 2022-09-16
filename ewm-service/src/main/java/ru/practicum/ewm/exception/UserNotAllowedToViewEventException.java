package ru.practicum.ewm.exception;

public class UserNotAllowedToViewEventException extends RuntimeException {
    public UserNotAllowedToViewEventException(String message) {
        super(message);
    }
}
