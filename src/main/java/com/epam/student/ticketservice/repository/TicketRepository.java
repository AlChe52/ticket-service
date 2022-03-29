package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository <TicketEntity, Long> {

      List <TicketEntity> getTicketEntityByPlaneEntityId (Long id);

      @Query (value = "SELECT * FROM ticket JOIN  plane ON plane.id",nativeQuery = true)
      List <TicketEntity> getTicketEntity (Long id);

}
