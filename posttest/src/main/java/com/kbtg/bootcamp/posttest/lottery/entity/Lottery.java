package com.kbtg.bootcamp.posttest.lottery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @Column(name = "ticket_id",length = 6)
    @Size(min = 6, max = 6)
    private String ticket;

    @Column(name = "amount")
    @NotNull
    private Integer amount;

    @Column(name = "price")
    @NotNull
    private Integer price;

    public Lottery() {
    }

    public Lottery(String ticket, Integer amount, Integer price) {
        this.ticket = ticket;
        this.amount = amount;
        this.price = price;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
