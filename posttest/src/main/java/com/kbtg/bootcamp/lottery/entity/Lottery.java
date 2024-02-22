package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @NotNull
    private String ticketNumber;
    @Column(name = "price")
    @NotNull
    private BigDecimal ticketPrice;

    @Column(name = "amount_available")
    @NotNull
    private Integer ticketAmount;
    @Column(name = "last_updated")
    @NotNull
    private LocalDateTime lastUpdate;

    public Lottery() {

    }

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public List<String> getTicketNumbers() {
        return Collections.singletonList(ticketNumber);
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
