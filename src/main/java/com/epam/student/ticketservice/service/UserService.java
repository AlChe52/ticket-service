package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);
    List <User> getAllUsers();
    void addUser (User user);
    void editUser(User user);
    void markUserToDelete(Long id);

}
