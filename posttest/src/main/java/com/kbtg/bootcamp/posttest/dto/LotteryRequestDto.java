package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LotteryRequestDto {

    @NotNull
    @Size(min = 6, max = 6 , message = "The number of ticket has to be 6 digits")
    private String ticket_id;
    private int price;
    private int amount;

    public LotteryRequestDto(String ticket_id, int price, int amount) {
        this.ticket_id = ticket_id;
        this.price = price;
        this.amount = amount;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
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
