package com.epam.student.ticketservice.testControllers;


import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.repository.PlaneRepository;
import com.epam.student.ticketservice.service.PlaneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestPlaneController {
    @MockBean
    private PlaneRepository planeRepository;

    @MockBean
    PlaneService planeService;


    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_GetAllPlanesFromCurrentData () throws Exception {
      Plane plane1 = new Plane(1L, "555", 5, LocalDate.of(2202,05,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);
      Plane plane2 = new Plane(2L, "666", 5, LocalDate.of(2202,06,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);


        when(planeService.getAllPlanesFromCurrentDate()).
                thenReturn(List.of(plane1,plane2));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/planes"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void test_GetPlaneById() throws Exception {
        Plane plane = new Plane(1L, "555", 5, LocalDate.of(2202,05,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);
        when(planeService.getPlaneById(plane.getId())).thenReturn(plane);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/planes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("555"));
    }

    @Test
    public void test_AddPlane() throws Exception {
        Plane plane = new Plane(1L, "555", 5, LocalDate.of(2202,05,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);
        mockMvc.perform(post("/planes")
                .content(mapper.writeValueAsString(plane))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_EditPlane() throws Exception {
        PlaneEntity planeEntity = new PlaneEntity(1L, "555", 5, LocalDate.of(2202,05,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);

        when(planeRepository.save(any(PlaneEntity.class))).thenReturn(planeEntity);

        Plane planeUpdate = new Plane(1L, "558",null,null,null,null,null,null,false);


        mockMvc.perform(put("/planes/1")
                .content(mapper.writeValueAsString(planeUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_MarkPlaneToDelete () throws Exception {
        PlaneEntity planeEntity = new PlaneEntity(1L, "555", 5, LocalDate.of(2202,05,05), Duration.ofMinutes(500), "Moscow", "NN" , null, false);
        when(planeRepository.save(any(PlaneEntity.class))).thenReturn(planeEntity);

        Plane planeUpdate = new Plane(1L,null ,null,null,null,null,null,null,true);

        mockMvc.perform(patch("/planes/1")
                .content(mapper.writeValueAsString(planeUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDeleted").value(true));
    }

    }



