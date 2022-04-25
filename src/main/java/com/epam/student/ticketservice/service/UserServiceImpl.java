package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.entity.PlaneEntity;
import com.epam.student.ticketservice.entity.TicketEntity;
import com.epam.student.ticketservice.entity.UserEntity;
import com.epam.student.ticketservice.exeptions.UserNotFoundException;
import com.epam.student.ticketservice.model.Plane;
import com.epam.student.ticketservice.model.Ticket;
import com.epam.student.ticketservice.model.User;
import com.epam.student.ticketservice.repository.PlaneRepository;
import com.epam.student.ticketservice.repository.TicketRepository;
import com.epam.student.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MapperFacade mapper;
    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Sorry, user nor found: id="+id));
            User  user =mapper.map(userEntity,User.class);

        user.setTickets(getTicketsForUser(id));

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList <User> users = new ArrayList<>();
        Iterable <UserEntity> iterable = userRepository.findAll();

        for (UserEntity userEntity: iterable) {
            User user = mapper.map(userEntity,User.class);
            user.setTickets(getTicketsForUser(user.getId()));

            users.add(user);
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        UserEntity userEntity = mapper.map(user,UserEntity.class);
        userEntity.setIsDeleted(Boolean.FALSE);
        userRepository.save(userEntity);
    }

    @Override
    public void editUser(User user) {
        if (!userRepository.existsById(user.getId()))
            throw new UserNotFoundException("User not found, id="+user.getId());
        UserEntity userEntity = mapper.map(user,UserEntity.class);
        userEntity.setIsDeleted(Boolean.FALSE);
        userRepository.save(userEntity);
    }

    @Override
    public void markUserToDelete(Long id) {
         User user = getUserById(id);
         UserEntity userEntity = mapper.map(user,UserEntity.class);
         userEntity.setIsDeleted(true);
         userRepository.save(userEntity);
    }

    private User getUserTempById (Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        User user = mapper.map(userEntity.get(), User.class);
       user.setTickets(null);
        return user;
    }

    private Plane getPlaneByTicketId (Long ticketId) {

        PlaneEntity planeEntity = planeRepository.findPlaneByTicketId(ticketId);
         planeEntity.setTickets(null);

        return mapper.map (planeEntity, Plane.class);
    }

    private List <Ticket> getTicketsForUser (Long userId) {
        List <Ticket> tickets = new ArrayList<>();
        Iterable <TicketEntity> iterable = ticketRepository.getTicketEntityByUserId(userId);
        User userTemp = getUserTempById(userId);

        for (TicketEntity ticketEntity: iterable) {
            Ticket ticket = mapper.map(ticketEntity,Ticket.class);
            ticket.setUser(userTemp);
            ticket.setPlane(getPlaneByTicketId(ticket.getId()));
            tickets.add(ticket);
        }
        return tickets;
    }


}
