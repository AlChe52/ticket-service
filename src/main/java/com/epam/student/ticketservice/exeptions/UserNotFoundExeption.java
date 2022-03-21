package com.epam.student.ticketservice.exeptions;

public class UserNotFoundExeption extends RuntimeException {
    public UserNotFoundExeption(String message) {
        super(message);
    }
    public UserNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
