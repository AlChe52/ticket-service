package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByPlaneId (Long id);

    List <Ticket> getTicketsByPlaneIdWithQuery (Long id,Boolean isSold);

    Ticket getTicketByIdWithPlaneId (Long planeid, Long ticketid);

    void editTicket (Ticket ticket);

    void markTicketToDelete (Long ticketid);


}
