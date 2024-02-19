package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    @Column(name = "ticket_number")
    private String ticketNumber;
    @Column(name = "price")
    private BigDecimal ticketPrice;

    @Column(name = "amount_available")
    private Integer ticketAmount;

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public List<String> getTicketNumbers() {
        return Collections.singletonList(ticketNumber);
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


}
