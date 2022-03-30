package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByPlaneId (Long id);

    List <Ticket> getTicketsByPlaneIdWithFilter (Long id, Boolean isSold);


}
