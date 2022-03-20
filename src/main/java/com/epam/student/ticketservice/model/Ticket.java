package com.epam.student.ticketservice.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Ticket {

    Long id;
    Plane plane;
    User user;
    BigDecimal price;
    Boolean isDeleted;

}
