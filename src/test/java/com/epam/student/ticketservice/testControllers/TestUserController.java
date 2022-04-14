package com.epam.student.ticketservice.testControllers;

import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

       @MockBean
       private UserRepository userRepository;
       @Autowired
       private ObjectMapper mapper;
       @Autowired
       private MockMvc mockMvc;

       @Test
       public void shouldGetNoAccountsWhenNoCreated() throws Exception {
              mockMvc.perform(get("/users"))
                      .andDo(print()).andExpect(status().isOk())
                      .andExpect(jsonPath("$").isEmpty());
       }

       @Test
       public void shouldGetAccountWhenCreated() throws Exception {
              UserEntity userEntity = new UserEntity(null,"Ivan","Petrov","220555",null,false);
              when(userRepository
                      .save(userEntity))
                      .thenReturn(new UserEntity(1L,"Ivan","Petrov","220555",null,false));

              mockMvc.perform(post("/users")
                      .content(mapper.writeValueAsString(userEntity))
                      .contentType(MediaType.APPLICATION_JSON)
                      .characterEncoding("utf-8"))
                      .andDo(print())
                      .andExpect(status().isOk());
//                      .andExpect(jsonPath("$.id").value(1L))
//                      .andExpect(jsonPath("$.firstname").value("Ivan"));
       }




}
