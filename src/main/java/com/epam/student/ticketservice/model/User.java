package com.epam.student.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    Long id;
    String firstname;
    String lastname;
    String passport;
    List<Ticket> tickets;
    Boolean isDeleted;


}
