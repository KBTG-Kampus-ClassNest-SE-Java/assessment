package com.kbtg.bootcamp.posttest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public UserTicket(Integer id) {
        this.id = id;
    }

    ;

}
