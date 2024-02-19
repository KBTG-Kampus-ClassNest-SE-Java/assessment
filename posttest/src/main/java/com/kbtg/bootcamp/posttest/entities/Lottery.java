package com.kbtg.bootcamp.posttest.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 6)
    private String ticketId;

    private Integer amount;

    private BigDecimal price;
}
