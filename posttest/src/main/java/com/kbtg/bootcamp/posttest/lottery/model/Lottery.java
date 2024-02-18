package com.kbtg.bootcamp.posttest.lottery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lottery")
@Builder
public class Lottery {

    @Id
    @Column(name = "ticket_id", length = 6)
    @Size(min = 10, max = 10)
    private String ticket;

    private Integer price;

    private Integer amount;
}
