package ru.practicum.ewm.exception;

public class UserAlreadyHasRequestException extends RuntimeException {
    public UserAlreadyHasRequestException(String message) {
        super(message);
    }
}
