package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundException;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.PlaneRepository;
import com.epam.student.ticketservice.repository.TicketRepository;
import com.epam.student.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository ;
    private final MapperFacade mapper;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public List<Plane> getAllPlanesFromCurrentDate() {
        List<Plane> planes = new ArrayList<>();
        Iterable <PlaneEntity>  iterable = planeRepository.getAllPlanesByCurrentDate();

         for (PlaneEntity planeEntity : iterable) {
             Plane plane = mapper.map(planeEntity,Plane.class);
             List <Ticket> tikets = getTicketList(plane);
             plane.setTickets(tikets);
             planes.add(plane);
         }
        return planes;
    }

    @Override
    public Plane getPlaneById(Long id) {
        PlaneEntity planeEntity = planeRepository.findById(id)
                              .orElseThrow(() -> new PlaneNotFoundException("Plane not found: id = " + id));
        Plane plane = mapper.map(planeEntity,Plane.class);

//
//        Plane planeTemp = getPlaneTempById(id);
//        List <Ticket> ticketList = new ArrayList<>();
//        Iterable <Ticket> iterable = plane.getTickets();
//        for (Ticket ticket: iterable) {
//            ticket.setPlane(planeTemp);
//           ticketList.add(ticket);
//        }
          plane.setTickets(getTicketList(plane));
          return plane;
    }

    @Override
    public void addPlane(Plane plane) {
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        List <TicketEntity> tickets = new ArrayList<>();
        for (int i = 0; i < planeEntity.getPlaces(); i++) {
            TicketEntity ticket = new TicketEntity();
            ticket.setPlaneEntity(planeEntity);
            ticket.setIsDeleted(Boolean.FALSE);
            ticket.setIsSold(Boolean.FALSE);
            tickets.add(ticket);
        }
        planeEntity.setIsDeleted(Boolean.FALSE);
        planeEntity.setTickets(tickets);
        planeRepository.save(planeEntity);
    }

    @Override
    public void editPlane(Plane plane) {
        if (!planeRepository.existsById(plane.getId()))
            throw new PlaneNotFoundException("Sorry, plane not found: id=" + plane.getId());
        List <Ticket> tickets = ticketService.getTicketsByPlaneId(plane.getId());
             if ((plane.getPlaces() - tickets.size()) < 0) {
                   tickets.removeAll(tickets.subList(plane.getPlaces(), tickets.size()));
                    }
        if ((plane.getPlaces() - tickets.size()) > 0) {
            for (int i = tickets.size(); i < plane.getPlaces(); i++) {
                Ticket ticket = new Ticket();
                ticket.setIsDeleted(Boolean.FALSE);
                ticket.setIsSold(Boolean.FALSE);
                tickets.add(ticket);
            }
        }
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
         List <TicketEntity> ticketEntityList = new ArrayList<>();
            for (int i = 0; i < tickets.size(); i++) {
                TicketEntity ticketEntity = mapper.map(tickets.get(i), TicketEntity.class);
                  ticketEntity.setPlaneEntity(planeEntity);
              ticketEntityList.add(ticketEntity);
                        }
        planeEntity.setIsDeleted(false);
           planeEntity.setTickets(ticketEntityList);
          planeRepository.save(planeEntity);
         }

    @Override
    public void markDeletePlane(Long id) {
        if (!planeRepository.existsById(id))
            throw new PlaneNotFoundException("Sorry, plane not found: id=" + id);
        PlaneEntity planeEntity = planeRepository.getById(id);
        planeEntity.setIsDeleted(true);
        planeRepository.save(planeEntity);

          Iterable <Ticket> iterable = ticketService.getTicketsByPlaneId(id);
        for ( Ticket ticket: iterable) {
            ticket.setIsDeleted(true);
            TicketEntity ticketEntity = mapper.map (ticket, TicketEntity.class);
            ticketEntity.setPlaneEntity(planeEntity);
            ticketRepository.save(ticketEntity);
        }
   }

     private List <Ticket> getTicketList (Plane plane) {
        List<Ticket> tickets = new ArrayList<>();
        Plane planeTemp = getPlaneTempById(plane.getId());
        List<Ticket> iterable = ticketService.getTicketsByPlaneId(plane.getId());
        for (Ticket ticket: iterable) {
             ticket.setPlane(planeTemp);
            UserEntity userEntity  = userRepository.findUserByTicketId(ticket.getId());
            User user =  mapper.map(userEntity, User.class);
            ticket.setUser(user);
             tickets.add(ticket);
        }

             return tickets;
    }

        private Plane getPlaneTempById (Long id) {
        Optional<PlaneEntity> planeEntity = planeRepository.findById(id);
        Plane plane = mapper.map(planeEntity.get(), Plane.class);
        plane.setTickets(null);
         return plane;
        }


}
