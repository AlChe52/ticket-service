package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundExeption;
import com.epam.student.ticketservice.model.Plane;
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
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository ;
    private final TicketRepository ticketRepository;
    private final MapperFacade mapper;

    @Override
    public List<Plane> getAllPlanesFromCurrentDate() {
        ArrayList<Plane> planes = new ArrayList<>();
        Iterable <PlaneEntity>  iterable = planeRepository.getAllPlanesByCurrentDate();
         for (PlaneEntity planeEntity : iterable) {
             Plane plane = mapper.map(planeEntity,Plane.class);
             plane.setTickets(getTicketList(plane));
             planes.add(plane);
         }
        return planes;
    }

    @Override
    public Plane getPlaneById(Long id) {
        PlaneEntity planeEntity = planeRepository.findById(id)
                .orElseThrow(() -> new PlaneNotFoundExeption("Sorry,plane nor found: id="+id));

        return mapper.map(planeEntity,Plane.class);
    }

    @Override
    public void addPlane(Plane plane) {
        plane.setTickets(getTicketList(plane));
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(Boolean.FALSE);
        planeRepository.save(planeEntity);
    }

    @Override
    public void editPlane(Plane plane) {
        if (!planeRepository.existsById(plane.getId()))
            throw new PlaneNotFoundExeption("Plane not found, id="+plane.getId());

        plane.setTickets(getTicketList(plane));
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(Boolean.FALSE);
        planeRepository.save(planeEntity);
    }

    @Override
    public void deletePlane(Long id) {
        Plane plane = getPlaneById(id);
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(true);
        planeRepository.save(planeEntity);
        List<TicketEntity> iterable = ticketRepository.getTicketEntity(plane.getId());
        for ( TicketEntity ticketEntity: iterable) {
            ticketEntity.setIsDeleted(true);
            ticketRepository.save(ticketEntity);
        }
    }

    private List <Ticket> getTicketList (Plane plane) {
          List<Ticket> tickets = new ArrayList<>();
         if (plane.getId()==null) {
              for (int i = 0; i < plane.getPlaces(); i++) {
                Ticket ticket = new Ticket();
                ticket.setIsDeleted(Boolean.FALSE);
                ticket.setIsSold(Boolean.FALSE);
                tickets.add(ticket);
            }
            return tickets;
        }

        List<TicketEntity> iterable = ticketRepository.getTicketEntity(plane.getId());

       for ( TicketEntity ticketEntity: iterable) {
            ticketEntity.setIsSold(false);
           tickets.add(mapper.map(ticketEntity,Ticket.class));
       }

       for (Ticket ticket: tickets) {
           ticket.setPlane(getPlaneByIdTicket(plane.getId()));
       }


        if ((plane.getPlaces() - tickets.size()) < 0) {
            tickets.removeAll(tickets.subList(plane.getPlaces(), tickets.size()));
            return tickets;
        }
        if ((plane.getPlaces() - tickets.size()) > 0) {
            for (int i = tickets.size(); i < plane.getPlaces(); i++) {
                Ticket ticket = new Ticket();
                ticket.setIsDeleted(Boolean.FALSE);
                ticket.setIsSold(Boolean.FALSE);
                tickets.add(ticket);
            }
            return tickets;
        }
        return tickets;
    }

        private Plane getPlaneByIdTicket (Long id) {

        Plane plane = mapper.map(planeRepository.findPlaneByTicketId(id), Plane.class);

        plane.setTickets(null);
         return plane;
        }
}
