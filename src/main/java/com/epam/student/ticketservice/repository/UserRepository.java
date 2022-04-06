package com.epam.student.ticketservice.repository;


import com.epam.student.ticketservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository <UserEntity,Long>{
}
