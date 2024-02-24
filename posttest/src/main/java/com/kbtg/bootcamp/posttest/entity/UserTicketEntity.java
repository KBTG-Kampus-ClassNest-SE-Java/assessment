package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

@Entity
@Table(name = "user_ticket", schema = "public")
public class UserTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_ticket_user_id_seq")
    @SequenceGenerator(name = "user_ticket_user_id_seq", sequenceName = "user_ticket_user_id_seq", allocationSize = 1
            , initialValue = 1000000000)
    @Digits(integer = 10, fraction = 0)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    public UserTicketEntity() {

    }

    public UserTicketEntity(Long userId, BigDecimal totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
