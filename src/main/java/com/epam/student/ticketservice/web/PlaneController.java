package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.dto.PlaneDTO;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.service.PlaneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Plane",description = "Plane Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneService planeService;
    private final MapperFacade mapper;

    @Operation (summary = "Get all planes from current dates", tags = "Plane")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Planes are found"
    ))
    @GetMapping
    List <Plane> getAllPlanesByCurrentDate (Pageable pageable) {
        return planeService.getAllPlanesFromCurrentDate(pageable);
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
        planeService.markDeletePlane(id);
    }

}
