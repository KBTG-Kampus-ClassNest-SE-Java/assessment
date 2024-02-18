package com.kbtg.bootcamp.posttest.lottery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Column(name = "ticket_id", length = 6)
    @Size(min = 10, max = 10)
    private String ticket;

    private Integer price;

    private Integer amount;
}
