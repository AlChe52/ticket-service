package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planes/{id}")
public class TicketController {

    private final TicketService ticketService;


    @GetMapping("/tickets")
    public List<Ticket> getTicketsByPlaneId(@PathVariable Long id,
                                            @RequestParam (required = false) Boolean isSold) {

        return ticketService.getTicketsByPlaneIdWithQuery(id,isSold);


    }
}
