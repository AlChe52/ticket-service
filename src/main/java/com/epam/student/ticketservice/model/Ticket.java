package com.epam.student.ticketservice.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {

    Long id;
    Plane plane;
    User user;
    BigDecimal price;
    Boolean isDeleted;
    Boolean isSold;

}
