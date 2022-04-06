package com.epam.student.ticketservice.service;


import com.epam.student.ticketservice.model.Plane;

import java.util.List;

public interface PlaneService {

    List<Plane> getAllPlanesFromCurrentDate();
    Plane getPlaneById (Long id);
    void addPlane (Plane plane);
    void editPlane (Plane plane);
    void markDeletePlane (Long id);




}
