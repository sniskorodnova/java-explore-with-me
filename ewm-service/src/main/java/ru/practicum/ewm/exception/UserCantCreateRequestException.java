package ru.practicum.ewm.exception;

public class UserCantCreateRequestException extends RuntimeException {
    public UserCantCreateRequestException(String message) {
        super(message);
    }
}
