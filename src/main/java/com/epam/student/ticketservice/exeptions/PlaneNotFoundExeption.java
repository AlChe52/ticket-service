package com.epam.student.ticketservice.exeptions;

public class PlaneNotFoundExeption extends RuntimeException {
    public PlaneNotFoundExeption(String message) {
        super(message);
    }

    public PlaneNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
