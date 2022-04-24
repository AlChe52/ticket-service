package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundException;
import com.epam.student.ticketservice.exeptions.TicketNotFoundException;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
import com.epam.student.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final MapperFacade mapper;


    @Override
    public List<Ticket> getTicketsByPlaneId(Long id) {
        List <Ticket> ticketList = new ArrayList<>();
        Optional<PlaneEntity> planeEntity = planeRepository.findById(id);
        Plane plane = mapper.map(planeEntity.get(), Plane.class);
        plane.setTickets(null);


        Iterable <TicketEntity> iterable = ticketRepository.getTicketEntity(id);
        for (TicketEntity ticketEntity:iterable) {
            Ticket  ticket = mapper.map(ticketEntity, Ticket.class);
            ticket.setPlane(plane);
            ticketList.add(ticket);
        }
        return ticketList;
     }

    @Override
    public List<Ticket> getTicketsByPlaneIdWithQuery(Long id, Boolean isSold) {
            if (isSold==null)
            return getTicketsByPlaneId(id);

        List <Ticket> tickets = new ArrayList<>();
        Optional<PlaneEntity> planeEntity = planeRepository.findById(id);
        Plane plane = mapper.map(planeEntity.get(), Plane.class);
        plane.setTickets(null);
         Iterable <TicketEntity> iterable = ticketRepository.getTicketsByQuery(id,isSold);
        for (TicketEntity ticketEntity:iterable) {
            Ticket  ticket = mapper.map(ticketEntity, Ticket.class);
             ticket.setPlane(plane);
             tickets.add(ticket);
        }
        return tickets;
    }

    @Override
    public Ticket getTicketByIdWithPlaneId(Long planeid, Long ticketid) {
           if (!ticketRepository.existsById(ticketid))
            throw new TicketNotFoundException("Sorry, ticket nor found: id="+planeid);
     TicketEntity ticketEntity = ticketRepository.findByPlaneEntityIdAndTicketEntityId(planeid,ticketid);

     Ticket ticket = mapper.map(ticketEntity,Ticket.class);

        return ticket;
    }

    @Override
    public void editTicket(Ticket ticket) {
      TicketEntity ticketEntity = mapper.map(ticket,TicketEntity.class);
      ticketRepository.save(ticketEntity);
    }

    @Override
    public void markTicketToDelete(Long ticketid) {
        TicketEntity ticketEntity = ticketRepository.findById(ticketid)
                .orElseThrow(() -> new PlaneNotFoundException("Ticket not found: id = " + ticketid));
        ticketEntity.setIsDeleted(true);
        ticketRepository.save(ticketEntity);
    }
  }



