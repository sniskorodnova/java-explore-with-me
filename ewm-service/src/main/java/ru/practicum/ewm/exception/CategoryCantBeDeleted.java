package ru.practicum.ewm.exception;

public class CategoryCantBeDeleted extends RuntimeException {
    public CategoryCantBeDeleted(String message) {
        super(message);
    }
}
