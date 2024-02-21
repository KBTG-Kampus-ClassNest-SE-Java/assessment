package com.kbtg.bootcamp.posttest.lottery;

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
    @Column(name = "lottery_id")
    private int lotteryId;

    @Column(name = "lottery_number", length = 6)
    @Size(min = 6, max = 6)
    private String lotteryNumber;

    private Integer price;

    private Integer amount;

}
