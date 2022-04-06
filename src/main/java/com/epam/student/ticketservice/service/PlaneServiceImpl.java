package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundExeption;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository ;
    private final MapperFacade mapper;
    private final TicketService ticketService;

    @Override
    public List<Plane> getAllPlanesFromCurrentDate() {
        List<Plane> planes = new ArrayList<>();
        List <Ticket> tikets = new ArrayList<>();

        Iterable <PlaneEntity>  iterable = planeRepository.getAllPlanesByCurrentDate();
         for (PlaneEntity planeEntity : iterable) {
             Plane plane = mapper.map(planeEntity,Plane.class);
              tikets = getTicketList(plane);
             plane.setTickets(tikets);
             planes.add(plane);
         }
        return planes;
    }

    @Override
    public Plane getPlaneById(Long id) {
        isFoundPlane(id);
        Optional<PlaneEntity> planeEntity = planeRepository.findById(id);
        Plane plane = mapper.map(planeEntity.get(),Plane.class);
        plane.setTickets(getTicketList(plane));

        return plane;
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
        isFoundPlane(plane.getId());
        plane.setTickets(getTicketList(plane));
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(false);
        planeRepository.save(planeEntity);
    }

    @Override
    public void markDeletePlane(Long id) {
        isFoundPlane(id);
        Plane plane = getPlaneByIdTicket(id);
        plane.setIsDeleted(true);

        List <Ticket> tickets = new ArrayList<>();
        Iterable <Ticket> iterable = ticketService.getTicketsByPlaneId(id);
        for ( Ticket ticket: iterable) {
            ticket.setIsDeleted(true);
            tickets.add(ticket);
        }
        plane.setTickets(tickets);
        planeRepository.save(mapper.map(plane,PlaneEntity.class));
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

        Plane planeTemp = getPlaneByIdTicket(plane.getId());
        List<Ticket> iterable = ticketService.getTicketsByPlaneId(plane.getId());
        for (Ticket ticket: iterable) {
            ticket.setPlane(planeTemp);
            tickets.add(ticket);
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
       Optional<PlaneEntity> planeEntity = planeRepository.findById(id);


        Plane plane = mapper.map(planeEntity.get(), Plane.class);

        plane.setTickets(null);

         return plane;
        }

    private void isFoundPlane(Long planeId) {
        if (!planeRepository.existsById(planeId))
            throw new PlaneNotFoundExeption("Sorry, plane nor found: id=" + planeId);


    }


}
