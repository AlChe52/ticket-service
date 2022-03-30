package com.epam.student.ticketservice.exeptions;

public class WrongQueryExeption extends RuntimeException{
    public WrongQueryExeption(String message) {
        super(message);
    }

    public WrongQueryExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
