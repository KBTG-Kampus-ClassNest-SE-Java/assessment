package com.kbtg.bootcamp.posttest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "amount")
    private Integer amount;
}
