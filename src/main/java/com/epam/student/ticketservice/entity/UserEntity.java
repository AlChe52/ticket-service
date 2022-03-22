package com.epam.student.ticketservice.entity;

import com.epam.student.ticketservice.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "user")
public class UserEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String passport;
//    @OneToMany
//    @JoinColumn (name = "ticket_id")
//    private List <TicketEntity> tickets;
    private Boolean isDeleted;

}
