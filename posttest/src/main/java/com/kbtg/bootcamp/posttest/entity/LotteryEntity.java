package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lottery")
public class LotteryEntity {


    @Id
    private String ticket;
    private int price;
    private int amount;
    private boolean status;

    public LotteryEntity() {
    }

    public LotteryEntity(String ticket, int price, int amount, boolean status) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
