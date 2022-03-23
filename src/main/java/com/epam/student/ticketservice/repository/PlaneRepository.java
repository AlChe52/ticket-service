package com.epam.student.ticketservice.repository;

import com.epam.student.ticketservice.entity.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository <PlaneEntity,Long> {
}
