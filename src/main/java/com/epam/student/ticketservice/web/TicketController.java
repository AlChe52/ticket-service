package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class TicketController {

    private final TicketService ticketService;


    @RequestMapping(value = "/planes/{id}/tickets", method = RequestMethod.GET )
    public List<Ticket> getTicketsByPlaneId(@PathVariable Long id,
                                            @RequestParam (required = false) Boolean isSold) {

        return ticketService.getTicketsByPlaneIdWithQuery(id,isSold);
    }

    @GetMapping("/planes/{planeid}/tickets/{ticketid}")
    public Ticket getTicketById (@PathVariable Long planeid, @PathVariable Long ticketid) {

        return ticketService.getTicketById(ticketid);
    }


}
