package com.epam.student.ticketservice.repository;


import com.epam.student.ticketservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository <UserEntity,Long>{

    @Query(value = "SELECT * FROM user JOIN TICKET ON user.id=user_id WHERE ticket.id= :id",nativeQuery = true)
    UserEntity findUserByTicketId (Long id);

}
