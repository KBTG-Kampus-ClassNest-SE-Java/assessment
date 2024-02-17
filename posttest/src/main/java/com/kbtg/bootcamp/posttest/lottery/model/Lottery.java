package com.kbtg.bootcamp.posttest.lottery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(length = 6)
    private String ticket;

    private Integer price;

    private Integer amount;
}
