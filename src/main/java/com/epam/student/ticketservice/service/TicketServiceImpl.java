package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final MapperFacade mapper;

    @Override
    public List<Ticket> getTicketsByPlaneId(Long id) {
           List <Ticket> tickets = new ArrayList<>();

     Iterable <TicketEntity> iterable = ticketRepository.getTicketEntity(id);
         for (TicketEntity ticketEntity:iterable) {
             tickets.add(mapper.map(ticketEntity, Ticket.class));
         }
        return tickets;
     }

    @Override
    public List<Ticket> getTicketsByPlaneIdWithFilter(Long id, Boolean isSold) {
        System.out.println(isSold);
        List <Ticket> tickets = new ArrayList<>();
        if (isSold==null) {
            Iterable<TicketEntity> iterable = ticketRepository.getTicketEntity(id);
            for (TicketEntity ticketEntity : iterable) {
                tickets.add(mapper.map(ticketEntity, Ticket.class));
            }
            return tickets;
        }
//        if (filter.equals(true) || filter.equals(false)) {
//            throw new WrongQueryExeption("Wrong query");
//        }



        Iterable <TicketEntity> iterable = ticketRepository.getTicketsByFilter(id, true);
        for (TicketEntity ticketEntity:iterable) {
            tickets.add(mapper.map(ticketEntity, Ticket.class));
        }

        return tickets;
    }

}


