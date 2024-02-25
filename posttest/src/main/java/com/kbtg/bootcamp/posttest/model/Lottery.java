package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Lottery")

public class Lottery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "TicketNumber",length = 6)
    private String ticketNumber;

    @Column(name = "Amount")
    private int amount;

    @Column(name = "Price")
    private int price;
    
    public Lottery() {
    	
    }


    public Lottery(String ticketNumber, int amount, int price) {

        this.ticketNumber = ticketNumber;
        this.amount = amount;
        this.price = price;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
