package com.kbtg.bootcamp.posttest.response;

import java.util.List;

public class UserTicketResponse {

    private List<String> tickets;
    private int amount;
    private int price;

    public UserTicketResponse(List<String> tickets, int amount, int price) {
        this.tickets = tickets;
        this.amount = amount;
        this.price = price;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
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
