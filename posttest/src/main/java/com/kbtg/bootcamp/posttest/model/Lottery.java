package com.kbtg.bootcamp.posttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_id")
    private Integer id;

    private String lottery_number;

    private int price;


    private int amount;

    @JsonIgnore
    @OneToMany(mappedBy = "lottery" )
    List<OrderLine> orderLines;


}
