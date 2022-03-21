package com.epam.student.ticketservice.web;

import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.UserRepository;
import com.epam.student.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;

    @GetMapping ("/{id}")
    public User getUserById (@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }
}