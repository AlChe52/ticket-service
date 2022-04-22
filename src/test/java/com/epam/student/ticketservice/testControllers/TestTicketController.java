package com.epam.student.ticketservice.testControllers;

import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.TicketRepository;
import com.epam.student.ticketservice.service.TicketService;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestTicketController {

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    TicketService ticketService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_GetAllTicketsByPlaneId () throws Exception {
        Ticket ticket1 = new Ticket(1L, null, null,new BigDecimal (50),false,false);
        Ticket ticket2 = new Ticket(2L, null, null,new BigDecimal (50),false,false);

        when(ticketService.getTicketsByPlaneIdWithQuery(1L,null)).
                thenReturn((List<Ticket>) List.of(ticket1,ticket2));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/planes/1/tickets"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));

    }
    @Test
    public void test_GetTicketById() throws Exception {
        Ticket ticket = new Ticket(1L, null, null,new BigDecimal (50),false,false);
        when(ticketService.getTicketByIdWithPlaneId(1L, ticket.getId())).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/planes/1/tickets/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("50"));
    }
    @Test
    public void test_EditTicket() throws Exception {
        TicketEntity ticketEntity = new TicketEntity(1L, null, null,new BigDecimal (50),false,false);
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        Ticket ticketUpdate = new Ticket(1L, null, null,new BigDecimal (150),false,true);

        mockMvc.perform(put("/tickets/1")
                .content(mapper.writeValueAsString(ticketUpdate))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_MarkTicketToDelete () throws Exception {
        TicketEntity ticketEntity = new TicketEntity(1L, null, null,new BigDecimal (50),false,false);
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);


        mockMvc.perform(patch("/tickets/1")
                .content(mapper.writeValueAsString(ticketEntity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    }



