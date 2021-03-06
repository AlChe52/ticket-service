package com.epam.student.ticketservice.web;

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
    private final MapperFacade mapper;

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
       userService.addUser(mapper.map(userDTO, User.class));
   }
   @PutMapping ("/{id}")
   public void editUser (@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = mapper.map(userDTO,User.class);
        user.setId(id);
        userService.editUser(user);
   }

   @PatchMapping ("/{id}")
   public void markUserToDelete (@PathVariable Long id) {
        userService.markUserToDelete(id);
   }

}
