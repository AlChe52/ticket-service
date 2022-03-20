package com.epam.student.ticketservice.model;

import lombok.Value;

import java.util.List;

@Value
public class User {

    Long id;
    String firstname;
    String lastname;
    String passport;
    List<Ticket> tickets;
    Boolean isDeleted;

}
