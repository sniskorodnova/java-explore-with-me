package ru.practicum.ewm.exception;

public class EventCantBeCreatedException extends RuntimeException {
    public EventCantBeCreatedException(String message) {
        super(message);
    }
}
