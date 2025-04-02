package ru.varno.CaloriesAPI.exceptions;

public class UserWithDuplicateEmailException extends RuntimeException {
    public UserWithDuplicateEmailException(String message) {
        super(message);
    }
}
