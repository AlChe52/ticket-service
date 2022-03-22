package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.mapper.UserDTOtoUserMapper;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import com.epam.student.ticketservice.dto.UserDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;
    private final UserDTOtoUserMapper mapper;

    @GetMapping ("/{id}")
    public User getUserById (@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
        }

   @PostMapping
    public void addUser (@RequestBody UserDTO userDTO) {
        userService.addUser(mapper.map(userDTO,User.class));
   }

}
