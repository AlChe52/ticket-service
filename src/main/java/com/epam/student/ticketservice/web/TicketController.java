package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.dto.TicketDTO;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class TicketController {

    private final TicketService ticketService;
    private final MapperFacade mapper;


    @RequestMapping(value = "/planes/{id}/tickets", method = RequestMethod.GET )
    public List<Ticket> getTicketsByPlaneId(@PathVariable Long id,
                                            @RequestParam (required = false) Boolean isSold) {

        return ticketService.getTicketsByPlaneIdWithQuery(id,isSold);
    }

    @RequestMapping(value = "/planes/{planeid}/tickets/{ticketid}", method = RequestMethod.GET)
    public Ticket getTicketById (@PathVariable Long planeid, @PathVariable Long ticketid) {

        return ticketService.getTicketByIdWithPlaneId(planeid, ticketid);
    }

    @RequestMapping(value = "/tickets/{ticketid}", method = RequestMethod.PUT)
    public void editTicket (@PathVariable Long ticketid, @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = mapper.map(ticketDTO, Ticket.class);
        ticket.setId(ticketid);
        ticketService.editTicket(ticket);
    }
    @RequestMapping(value = "/tickets/{ticketid}", method = RequestMethod.PATCH)
    public void markTicketToDelete (@PathVariable Long ticketid) {

        ticketService.markTicketToDelete(ticketid);
    }
}
