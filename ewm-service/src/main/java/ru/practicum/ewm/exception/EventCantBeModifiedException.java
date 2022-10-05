package ru.practicum.ewm.exception;

public class EventCantBeModifiedException extends RuntimeException {
    public EventCantBeModifiedException(String message) {
        super(message);
    }
}
