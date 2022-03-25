//package com.epam.student.ticketservice.web;
//
//import com.epam.student.ticketservice.model.Ticket;
//import com.epam.student.ticketservice.service.TicketService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping
//public class TicketController {
//
//    private final TicketService ticketService;
//
//
//    @GetMapping (name = "/planes/{id}/tickets/{id}")
//    public Ticket getTicketById(@PathVariable Long id) {
//        return ticketService.getTicketById(id);
//
//
//    }
//}
