package com.epam.student.ticketservice.exeptions;

public class TicketNotFoundExeption extends RuntimeException{
    public TicketNotFoundExeption(String message) {
        super(message);
    }

    public TicketNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
