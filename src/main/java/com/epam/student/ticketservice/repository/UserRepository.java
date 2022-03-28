package com.epam.student.ticketservice.repository;


import com.epam.student.ticketservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity,Long>{
}
