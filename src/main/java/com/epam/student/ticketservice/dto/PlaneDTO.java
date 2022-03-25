package com.epam.student.ticketservice.dto;

import lombok.Data;
import java.time.Duration;
import java.time.LocalDate;


@Data
public class PlaneDTO {
    private String name;
    private Integer places;
    private LocalDate depart;
   // private Duration duration;
    private String fromm;
    private String to;

}
