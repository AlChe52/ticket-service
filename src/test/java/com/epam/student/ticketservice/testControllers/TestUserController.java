package com.epam.student.ticketservice.testControllers;

import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.repository.UserRepository;
import com.epam.student.ticketservice.service.UserService;
import com.epam.student.ticketservice.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
       @Autowired
       private UserController userController;

       @Test
       public void shouldGetNoUserWhenNoCreated() throws Exception {
              mockMvc.perform(get("/users"))
                      .andDo(print()).andExpect(status().isOk())
                      .andExpect(jsonPath("$").isEmpty());
       }

       @Test
       public void shouldGetUserWhenCreated() throws Exception {
              UserEntity userEntity = new UserEntity(null, "Ivan", "Petrov", "220555", null, false);
              Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                      .thenReturn(userEntity);


              MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users")
                      .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                      .characterEncoding("UTF-8").content(this.mapper.writeValueAsString(userEntity));

              mockMvc.perform(builder).andDo(print()).andExpect(status().isOk());//.andExpect(jsonPath("$.firstname",is ("Ivan")))
              //      .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(userEntity)));
       }


//              mockMvc.perform(post("/users")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(mapper.writeValueAsString(userEntity)))
//
//       //               .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
////                      .characterEncoding("utf-8"))
////                      .andDo(print())
//                      .andExpect(status().isOk())
//
//                     .andExpect(MockMvcResultMatchers.jsonPath("$[0].id",is(1L)))
//                      .andExpect(jsonPath("$.firstname",is("Ivan")));
//              verify(userRepository, times(1)).save(any(UserEntity.class));

       @Test
       public void getPatientById_success() throws Exception {
              UserEntity userEntity = new UserEntity(1L, "Ivan", "Petrov", "220555", null, false);
              Mockito.when(userRepository.findById(userEntity.getId())).thenReturn(java.util.Optional.of(userEntity));

              mockMvc.perform(MockMvcRequestBuilders
                      .get("/users/1")
                      .contentType(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk());
//                      .andExpect(jsonPath("$", notNullValue()))
//                      .andExpect(jsonPath("$.firstname", is("Ivan")));

       }

}


