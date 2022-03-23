package com.epam.student.ticketservice.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    Long id;
    String firstname;
    String lastname;
    String passport;
    List<Ticket> tickets;
    Boolean isDeleted;

}
