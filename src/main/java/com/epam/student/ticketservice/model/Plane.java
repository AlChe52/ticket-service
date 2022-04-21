package com.epam.student.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plane {
    Long id;
    String name;
    Integer places;
    LocalDate depart;
    Duration duration;
    String from;
    String to;
    List <Ticket> tickets;
    Boolean isDeleted;

}
