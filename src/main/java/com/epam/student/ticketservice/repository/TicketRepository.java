package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository <TicketEntity, Long> {


    List<TicketEntity> findAllTicketsByPlaneId(Long planeId);
}
