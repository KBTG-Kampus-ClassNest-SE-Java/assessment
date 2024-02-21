package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    private String ticket;
    private int price;

    private int amount;

    public Lottery(String ticket, int price, int amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }
    public Lottery(){}

    public String getTicket() {
        return ticket;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}


