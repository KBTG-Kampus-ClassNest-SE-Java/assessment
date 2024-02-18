package com.kbtg.bootcamp.posttest.lottery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "lottery")
public class LotteryTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ticket;
    private int price;
    private int amount;

}
