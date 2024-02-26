package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
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
    private int id;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price")
    private double price;

}
