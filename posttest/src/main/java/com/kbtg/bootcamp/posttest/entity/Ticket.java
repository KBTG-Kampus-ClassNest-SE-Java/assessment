package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "lottery")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    @Id
    String ticket_no;

    @Column(name = "price")
    int price;

    @Column(name = "amount")
    int amount;

    @OneToMany(mappedBy = "ticket")
    private List<UserTicket> userTickets;
}
