package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.exeptions.PlaneNotFoundExeption;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.repository.PlaneRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository ;
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
        System.out.println(plane);
      ArrayList <Ticket> tickets = new ArrayList<>();
      int k=plane.getPlaces();
        for (int i = 0; i <k; i++) {
          tickets.add(new Ticket());
        }
        plane.setTickets(tickets);
        PlaneEntity planeEntity = mapper.map(plane,PlaneEntity.class);
        planeEntity.setIsDeleted(Boolean.FALSE);
        planeRepository.save(planeEntity);
    }


    @Override
    public void editPlane(Plane plane) {

    }

    @Override
    public void deletePlane(Long id) {

    }
}
