package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.exeptions.UserNotFoundExeption;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MapperFacade mapper;

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("Sorry, user nor found: id="+id));

        return mapper.map(userEntity,User.class);
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList <User> users = new ArrayList<>();
        Iterable <UserEntity> iterable = userRepository.findAll();
        for (UserEntity userEntity: iterable) {
            users.add(mapper.map(userEntity,User.class));
        }


        return users;
    }

    @Override
    public void addUser(User user) {
        UserEntity userEntity = mapper.map(user,UserEntity.class);

    }
}
