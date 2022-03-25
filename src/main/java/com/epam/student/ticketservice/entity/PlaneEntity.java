package com.epam.student.ticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plane")
public class PlaneEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer places;
    private LocalDate depart;
   // private Duration duration;
    private String fromm;
    private String to;
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
   // @JoinColumn (name = "ticket_id")
    private List<TicketEntity> tickets;
    private Boolean isDeleted;


}
