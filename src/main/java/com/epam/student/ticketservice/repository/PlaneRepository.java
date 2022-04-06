package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRepository extends JpaRepository <PlaneEntity,Long> {


    @Query (value = "SELECT * FROM plane WHERE depart>=NOW()",nativeQuery = true)
    List<PlaneEntity> getAllPlanesByCurrentDate();

    @Query (value = "SELECT * FROM PLANE JOIN TICKET WHERE plane.id=:id",nativeQuery = true)
    PlaneEntity findPlaneByTicketId (Long id);
}
