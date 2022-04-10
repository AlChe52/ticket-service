package com.epam.student.ticketservice.entity;

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
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstname;
    private String lastname;
    private String passport;
     @OneToMany (mappedBy = "userEntity",cascade = CascadeType.ALL, orphanRemoval = true)
     private List<TicketEntity> tickets;

    private Boolean isDeleted;

}
