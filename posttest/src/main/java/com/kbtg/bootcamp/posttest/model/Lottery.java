package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String lottery_number;

    private int price;


    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;


}
