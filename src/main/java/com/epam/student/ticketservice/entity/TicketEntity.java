package com.epam.student.ticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
   // @JoinColumn (name = "plane_id")
    private PlaneEntity plane;
    @OneToOne
    @JoinColumn (name = "user_id")
    private UserEntity user;
    private BigDecimal price;
    private Boolean isDeleted;
    private Boolean isSold;

}
