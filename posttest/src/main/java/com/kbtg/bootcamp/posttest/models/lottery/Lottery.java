package com.kbtg.bootcamp.posttest.models.lottery;

import com.kbtg.bootcamp.posttest.models.userTicket.UserTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "lottery")
@Getter
@Setter
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ticket", length = 6, nullable = false)
    private String ticket;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

//    @Column(name = "soldAmount")
//    private Integer soldAmount;

    @OneToMany(mappedBy = "lottery", fetch = FetchType.LAZY)
    private List<UserTicket> userTickets;

    public Lottery() {
    }

    public Lottery(String ticket, Double price, Integer amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }
}
