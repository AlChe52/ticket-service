package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundExeption;
import com.epam.student.ticketservice.exeptions.UserNotFoundExeption;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import com.epam.student.ticketservice.repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository ;
  //  private final TicketRepository ticketRepository;
    private final MapperFacade mapper;

    @Override
    public List<Plane> getAllPlanesFromCurrentDate() {
        ArrayList<Plane> planes = new ArrayList<>();
        Iterable <PlaneEntity>  iterable = planeRepository.getAllPlanesByCurrentDate();
         for (PlaneEntity planeEntity : iterable) {

             planes.add(mapper.map(planeEntity,Plane.class));

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
            throw new UserNotFoundExeption("Plane not found, id="+plane.getId());

        plane.setTickets(getTicketList(plane));
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(Boolean.FALSE);
        planeRepository.save(planeEntity);
    }

    @Override
    public void deletePlane(Long id) {


    }

    private List <Ticket> getTicketList (Plane plane) {
        List<Ticket> tickets = new ArrayList<>();
       if (plane.getId() == null) {
              for (int i = 0; i < plane.getPlaces(); i++) {
                Ticket ticket = new Ticket();
                ticket.setIsDeleted(Boolean.FALSE);
                ticket.setIsSold(Boolean.FALSE);
                tickets.add(ticket);
            }
            return tickets;
        }

        tickets.addAll(getTicketList(getPlaneById(plane.getId())));
        for (int i = 0; i < tickets.size() ; i++) {
            System.out.println(tickets.get(i));

        }

        if ((plane.getPlaces().intValue() - tickets.size()) < 0) {
            tickets.removeAll(tickets.subList(plane.getPlaces(), tickets.size()));
        }
        if ((plane.getPlaces().intValue() - tickets.size()) > 0) {
            for (int i = tickets.size(); i < plane.getPlaces().intValue(); i++) {
                Ticket ticket = new Ticket();
                ticket.setIsDeleted(Boolean.FALSE);
                ticket.setIsSold(Boolean.FALSE);
                tickets.add(ticket);
            }
        }
        return tickets;
    }

}
