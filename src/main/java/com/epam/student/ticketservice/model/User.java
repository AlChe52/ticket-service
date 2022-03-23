package com.epam.student.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
