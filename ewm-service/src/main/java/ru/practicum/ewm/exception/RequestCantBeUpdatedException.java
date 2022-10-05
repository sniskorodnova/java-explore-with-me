package ru.practicum.ewm.exception;

public class RequestCantBeUpdatedException extends RuntimeException {
    public RequestCantBeUpdatedException(String message) {
        super(message);
    }
}
