package com.kbtg.bootcamp.posttest.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class UserTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10)
    private String userId;

    @Column(length = 6)
    private String ticketId;

    private BigDecimal buyPrice;

    private LocalDateTime buyDate;

}
