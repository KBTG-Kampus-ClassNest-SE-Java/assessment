package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lottery")
public class LotteryEntity {

    // properties
    @Id
    private String ticket;
    private int price;
    private int amount;

    public LotteryEntity() {

    }

    public LotteryEntity(String ticket, int price, int amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
