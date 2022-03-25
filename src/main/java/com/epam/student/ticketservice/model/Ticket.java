package com.epam.student.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    Long id;
    Plane plane;
    User user;
    BigDecimal price;
    Boolean isDeleted;
    Boolean isSold;

}
