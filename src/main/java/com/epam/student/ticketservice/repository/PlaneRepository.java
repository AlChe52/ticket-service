package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaneRepository extends JpaRepository <PlaneEntity,Long> {

    @Query (value = "SELECT * FROM plane WHERE depart >=NOW()",nativeQuery = true)
    List<PlaneEntity> getAllPlanesByCurrentDate();

    @Query (value = "SELECT * FROM plane JOIN  ticket WHERE plane.id= :id",nativeQuery = true)
    PlaneEntity findPlaneByTicketId (long id);
}
