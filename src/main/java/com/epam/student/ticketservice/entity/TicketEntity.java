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
@Table (name ="ticket")
public class TicketEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "plane_id")
    private PlaneEntity planeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private UserEntity userEntity;

    private BigDecimal price;

    private Boolean isDeleted;

    private Boolean isSold;

}
