package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.Plane;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    @Override
    public List<Plane> getAllPlanesFromCurrentDate() {
        return null;
    }

    @Override
    public Plane getPlaneById(Long id) {
        return null;
    }

    @Override
    public void addPlane(Plane plane) {

    }

    @Override
    public void editPlane(Plane plane) {

    }

    @Override
    public void deletePlane(Long id) {

    }
}
