package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_ticket_id")
    private UserTicket userTickets;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lottery_id")
    private Lottery lottery;



}
