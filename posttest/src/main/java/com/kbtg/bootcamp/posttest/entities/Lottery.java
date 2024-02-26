package com.kbtg.bootcamp.posttest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lottery")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lottery {

    @Id
    @Column(name = "ticket")
    private String ticket;

    @Column(name = "price")
    private Integer price;

    @Column(name = "amount")
    private Integer amount;

}
