package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.User;

import java.util.List;

public interface UserService {

    /**
     * Поиск пользователя по ID
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * Поиск всех пользователей
     * @return
     */
    List <User> getAllUsers();

    /**
     * Добавление полизователя
     * @param user
     */
    void addUser (User user);

    /**
     * Редактирование пользователя
     * @param user
     */
    void editUser(User user);

    /**
     * Помечает пользователя на удаление, isDeleted = true
      * @param id
     */
    void markUserToDelete(Long id);

}
