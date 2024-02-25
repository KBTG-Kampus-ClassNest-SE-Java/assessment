package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.user.UserTicket;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "lottery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price")
    private double price;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "lottery_id")
//    private int lotteryId;
//
//    @Column (name = "lottery_number")
//    @Size(min = 6, max = 6)
//    private String ticket;
//
//    private double price;
//
//    private double amount;



}
