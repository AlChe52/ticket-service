package com.epam.student.ticketservice.testControllers;

import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.UserRepository;
import com.epam.student.ticketservice.service.UserService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestUserController {

       @MockBean
       private UserRepository userRepository;

       @MockBean
       UserService userService;


       @Autowired
       private ObjectMapper mapper;
       @Autowired
       private MockMvc mockMvc;

       @Test
       public void test_GetAllUsers () throws Exception {
              when(userService.getAllUsers()).
                      thenReturn(List.of(new User(1L, "Ivan", "Petrov", "220555", null, false),
                                         new User(2L, "Sidor", "Ivanov", "235789", null, false)));
              mockMvc
                      .perform(MockMvcRequestBuilders.get("/users"))
                      .andDo(print())
                      .andExpect(MockMvcResultMatchers.status().isOk())
                      .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                      .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value("Petrov"))
                      .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                      .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstname").value("Sidor"))
                      .andExpect(MockMvcResultMatchers.jsonPath("$[1].passport").value("235789"));
       }


       @Test
       public void test_GetNoUserWhenNoCreated() throws Exception {
             mockMvc.perform(get("/users"))
                      .andDo(print()).andExpect(status().isOk())
                      .andExpect(jsonPath("$").isEmpty());
       }

       @Test
       public void test_GetUserById() throws Exception {
              User user = new User(1L, "Ivan", "Petrov", "220555", null, false);
              when(userService.getUserById(user.getId())).thenReturn(user);

             mockMvc.perform(MockMvcRequestBuilders
                      .get("/users/1"))
                     .andDo(print())
                      .andExpect(status().isOk())
                      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                      .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Ivan"));
       }

       @Test
       public void test_UserNotFound () throws Exception {
              mockMvc.perform(get("/users/5"))
                      .andDo(print())
                      .andExpect(status().isNotFound());
       }

       @Test
       public void test_AddUser() throws Exception {
                   User user = new User(1L, "Ivan", "Petrov", "220555", null, false);

                    mockMvc.perform(post("/users")
                      .content(mapper.writeValueAsString(user))
                      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                      .andDo(print())
                      .andExpect(status().isOk());
       }

       @Test
       public void test_EditUser() throws Exception {
        UserEntity userEntity = new UserEntity(1L, "Ivan", "Petrov", "220555", null, false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

              User userUpdate = new User(1L, "Ivan", "Kozlov", "220555", null, false);

              mockMvc.perform(put("/users/1")
                      .content(mapper.writeValueAsString(userUpdate))
                      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                      .andDo(print())
                      .andExpect(status().isOk());
             }

       @Test
       public void test_MarkUserToDelete () throws Exception {
              UserEntity userEntity = new UserEntity(1L, "Ivan", "Petrov", "220555", null, false);
              when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

              mockMvc.perform(patch("/users/1")
                      .content(mapper.writeValueAsString(userEntity))
                      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                      .andDo(print())
                      .andExpect(status().isOk());
             }

       }




