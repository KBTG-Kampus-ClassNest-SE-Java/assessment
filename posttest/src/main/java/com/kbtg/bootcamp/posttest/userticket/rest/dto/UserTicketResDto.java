package com.kbtg.bootcamp.posttest.userticket.rest.dto;

import java.util.List;

public class UserTicketResDto {
    private List<String> tickets;
    private int count;
    private int totalPrice;

    public UserTicketResDto() {
    }

    public UserTicketResDto(List<String> tickets, int count, int totalPrice) {
        this.tickets = tickets;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
