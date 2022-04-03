package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundExeption;
import com.epam.student.ticketservice.exeptions.TicketNotFoundExeption;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
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
    private final PlaneRepository planeRepository;
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
    public List<Ticket> getTicketsByPlaneIdWithQuery(Long id, Boolean isSold) {
            if (isSold==null)
            return getTicketsByPlaneId(id);

        List <Ticket> tickets = new ArrayList<>();
        Iterable <TicketEntity> iterable = ticketRepository.getTicketsByQuery(id,isSold);
        for (TicketEntity ticketEntity:iterable) {
            tickets.add(mapper.map(ticketEntity, Ticket.class));
        }
        return tickets;
    }

    @Override
    public Ticket getTicketByIdWithPlaneId(Long planeid, Long ticketid) {
      if (!planeRepository.existsById(planeid))
              throw new PlaneNotFoundExeption("Sorry, plane nor found: id="+planeid);
     isFoundTicket(ticketid);

     TicketEntity ticketEntity = ticketRepository.findByPlaneEntityIdAndTicketEntityId(planeid,ticketid);
        return mapper.map(ticketEntity,Ticket.class);
    }

    @Override
    public void editTicket(Ticket ticket) {
      isFoundTicket(ticket.getId());
      TicketEntity ticketEntity = mapper.map(ticket,TicketEntity.class);
      ticketRepository.save(ticketEntity);
    }

    @Override
    public void markTicketToDelete(Long ticketid) {
        isFoundTicket(ticketid);
        TicketEntity ticketEntity = ticketRepository.getById(ticketid);
        ticketEntity.setIsDeleted(true);
        ticketRepository.save(ticketEntity);

    }

    private void isFoundTicket (Long ticketId) {
        if (!ticketRepository.existsById(ticketId))
            throw new TicketNotFoundExeption("Sorry, ticket nor found: id=" + ticketId);


    }



}


