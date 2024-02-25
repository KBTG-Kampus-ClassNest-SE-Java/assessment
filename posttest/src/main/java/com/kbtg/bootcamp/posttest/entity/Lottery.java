package com.kbtg.bootcamp.posttest.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer id;

    @Column(name = "ticket_number")
    private String ticketNumber;
    private int price;
    private int amount;

    public Lottery(String ticketNumber, int price, int amount) {
        this.ticketNumber = ticketNumber;
        this.price = price;
        this.amount = amount;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "lottery")
    List<UserTicket> userTickets;
}
