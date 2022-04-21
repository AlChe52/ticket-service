package com.epam.student.ticketservice.testControllers;


import com.epam.student.ticketservice.repository.PlaneRepository;
import com.epam.student.ticketservice.repository.UserRepository;
import com.epam.student.ticketservice.service.PlaneService;
import com.epam.student.ticketservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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

    }


}
