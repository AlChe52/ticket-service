package com.epam.student.ticketservice.model;

import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Value
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
