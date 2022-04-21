package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository <TicketEntity, Long> {


      @Query (value = "SELECT * FROM ticket WHERE plane_id= :planeid",nativeQuery = true)
      List <TicketEntity> getTicketEntity (Long planeid);


      @Query (value = "SELECT * FROM ticket JOIN plane ON plane.id= :id WHERE is_Sold= :isSold",nativeQuery = true)
      List <TicketEntity> getTicketsByQuery (@Param("id") Long id, @Param("isSold") Boolean isSold);

     @Query (value = "SELECT t.*,p.* FROM ticket t JOIN plane p WHERE p.id = :planeid AND t.id= :ticketid", nativeQuery = true)

     TicketEntity findByPlaneEntityIdAndTicketEntityId (Long planeid, Long ticketid);

}
