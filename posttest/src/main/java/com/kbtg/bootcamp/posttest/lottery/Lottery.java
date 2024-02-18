package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ticket_number;
    private Integer price;
    private Integer amount;

    public Lottery() {
    }
}

