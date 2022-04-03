package com.epam.student.ticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "plane_id")
    private PlaneEntity planeEntity;

    @ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private UserEntity userEntity;

    private BigDecimal price;
    private Boolean isDeleted;
    private Boolean isSold;

}
