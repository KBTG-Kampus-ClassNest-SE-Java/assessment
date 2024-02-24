package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;


@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @Column(name = "id",length = 6, nullable = false)
    private String id;

    @Column(name = "ticket",length = 6,nullable = false)
    private String ticket;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(name = "amount",nullable = false)
    private int amount;

    public Lottery() {
    }

    public Lottery(String id, String ticket, int price, int amount) {
        this.id = id;
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
