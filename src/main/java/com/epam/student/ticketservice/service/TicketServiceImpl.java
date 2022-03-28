package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.TicketNotFoundExeption;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final MapperFacade mapper;

    @Override
    public Ticket getTicketById(Long id) {
       TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundExeption("Sorry, ticket nor found: id="+id));
        return mapper.map(ticketEntity, Ticket.class);
     }

    }


