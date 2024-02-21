package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_id")
    private Integer id;

    private String lottery_number;

    private int price;


    private int amount;

    public Lottery(String lottery_number, int price, int amount) {
        this.lottery_number = lottery_number;
        this.price = price;
        this.amount = amount;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "lottery" )
    List<OrderLine> orderLines;


}
