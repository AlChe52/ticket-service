package com.epam.student.ticketservice.testControllers;

import com.epam.student.ticketservice.web.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RequiredArgsConstructor
public class TestUserController {

       private final MockMvc mockMvc;
       private final UserController userController;





}
