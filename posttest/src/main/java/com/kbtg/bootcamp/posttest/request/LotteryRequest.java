package com.kbtg.bootcamp.posttest.request;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class LotteryRequest {
    @NotNull
    @Length(min = 6,max = 6,message = "Ticket number could be 6 characters")
    private String ticket;

    @NotNull
    private int price;

    @NotNull
    private int amount;


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
