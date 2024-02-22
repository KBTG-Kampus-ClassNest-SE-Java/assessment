package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.NonNull;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_id")
    private Integer id;

    @NonNull
    @Column(name = "lottery_number")
    private String lotteryNumber;

    private int price;


    private int amount;

    public Lottery(String lotteryNumber, int price, int amount) {
        this.lotteryNumber = lotteryNumber;
        this.price = price;
        this.amount = amount;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "lottery" )
    List<UserTicket> userTickets;


}
