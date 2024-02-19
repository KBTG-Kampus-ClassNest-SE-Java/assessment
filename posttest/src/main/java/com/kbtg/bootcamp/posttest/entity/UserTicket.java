package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_lottery")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTicket {
    @Id
    int id;

    @Column(name = "user_id")
    String userId;

    @ManyToOne
    @JoinColumn(name = "ticket_no")
    private Ticket ticket;


}
