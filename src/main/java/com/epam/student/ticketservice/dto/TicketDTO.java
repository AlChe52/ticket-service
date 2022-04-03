package com.epam.student.ticketservice.dto;

import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDTO {
    private Plane plane;
    private User user;
    private BigDecimal price;
    private Boolean isSold;

}
