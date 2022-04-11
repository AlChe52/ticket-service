package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundException;
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
         PlaneEntity planeEntity = planeRepository.getById(id);

           Iterable <TicketEntity> iterable = ticketRepository.getTicketEntity(id);
           for (TicketEntity ticketEntity: iterable) {
              ticketEntity.setPlaneEntity(planeEntity);
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
              throw new PlaneNotFoundException("Sorry, plane nor found: id="+planeid);

     TicketEntity ticketEntity = ticketRepository.findByPlaneEntityIdAndTicketEntityId(planeid,ticketid);
        return mapper.map(ticketEntity,Ticket.class);
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



