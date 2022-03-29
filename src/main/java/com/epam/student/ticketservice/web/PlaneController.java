package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.dto.PlaneDTO;
import com.epam.student.ticketservice.dto.UserDTO;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.service.PlaneService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneService planeService;
    private final MapperFacade mapper;

    @GetMapping
    List <Plane> getAllPlanesByCurrentDate () {
        return planeService.getAllPlanesFromCurrentDate();
    }

    @GetMapping("/{id}")
    Plane getPlaneById (@PathVariable Long id) {
        return planeService.getPlaneById(id);
    }

    @PostMapping
    public void addPlane(@RequestBody PlaneDTO planeDTO) {
        System.out.println(planeDTO);
        planeService.addPlane(mapper.map(planeDTO,Plane.class));
    }

    @PutMapping ("/{id}")
    public void editPlane (@PathVariable Long id, @RequestBody PlaneDTO planeDTO){
        Plane plane = mapper.map(planeDTO,Plane.class);
        plane.setId(id);
        planeService.editPlane(plane);
    }

    @PatchMapping ("/{id}")
    public void deletePlane (@PathVariable Long id) {
        planeService.deletePlane(id);
    }

}
