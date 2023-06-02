package ru.practicum.shareit.exception;

public class UserValidException extends RuntimeException {
    public UserValidException(String message) {
        super(message);
    }
}
