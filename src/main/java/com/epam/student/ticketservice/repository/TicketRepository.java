package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository <TicketEntity, Long> {


      @Query (value = "SELECT * FROM ticket JOIN  plane ON plane.id",nativeQuery = true)
      List <TicketEntity> getTicketEntity (Long id);

      @Query (value = "SELECT * FROM ticket WHERE is_Sold= :isSold JOIN plane ON plane.id= :id",nativeQuery = true)
      List <TicketEntity> getTicketsByQuery (@Param("id") Long id, @Param("isSold") Boolean isSold);
}
