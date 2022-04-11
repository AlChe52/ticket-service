package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundException;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
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
        Plane planeTemp = getPlaneByIdTicket(id);
        List <Ticket> ticketList = new ArrayList<>();
        Iterable <Ticket> iterable = plane.getTickets();
        for (Ticket ticket: iterable) {
            ticket.setPlane(planeTemp);
           ticketList.add(ticket);
        }
           plane.setTickets(ticketList);
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

//        Iterable <Ticket> iterable = ticketService.getTicketsByPlaneId(plane.getId());
//        Plane planeTemp = getPlaneByIdTicket(plane.getId());
//         for (Ticket ticket: iterable) {
//             ticket.setPlane(planeTemp);
//               ticketList.add(ticket);
//         }

      //  planeRepository.save(planeEntity);
        List <Ticket> tickets = ticketService.getTicketsByPlaneId(plane.getId());

       // Plane planeTemp = getPlaneByIdTicket(plane.getId());
             if ((plane.getPlaces() - tickets.size()) < 0) {
                 System.out.println(plane.getPlaces() - tickets.size());
            tickets.removeAll(tickets.subList(plane.getPlaces(), tickets.size()));
                 System.out.println(tickets.size());
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
                System.out.println(ticketEntityList.get(i));
            }
        planeEntity.setIsDeleted(false);
           planeEntity.setTickets(ticketEntityList);

          planeRepository.save(planeEntity);
         }




    @Override
    public void markDeletePlane(Long id) {

     //   isFoundPlane(id);
        Optional<PlaneEntity> plane = planeRepository.findById(id);
        System.out.println(id);
        System.out.println(plane.get());
//
//
//        List <Ticket> tickets = new ArrayList<>();
//        Iterable <Ticket> iterable = ticketService.getTicketsByPlaneId(id);
//        for ( Ticket ticket: iterable) {
//            ticket.setIsDeleted(true);
//           ticket.setPlane(plane);
//            tickets.add(ticket);
//        }
//        plane.setTickets(tickets);
       // PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        plane.get().setIsDeleted(Boolean.TRUE);
        System.out.println(plane.get());
        planeRepository.save(plane.get());
    }

    private List <Ticket> getTicketList (Plane plane) {
        List<Ticket> tickets = new ArrayList<>();
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
                ticket.setPlane(planeTemp);
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
            throw new PlaneNotFoundException("Sorry, plane nor found: id=" + planeId);


    }


}
