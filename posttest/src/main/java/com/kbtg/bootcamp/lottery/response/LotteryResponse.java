package com.kbtg.bootcamp.lottery.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class LotteryResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ticket;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> tickets;


    public LotteryResponse(List<String> tickets) {
        this.tickets = tickets;
    }

    public LotteryResponse(String ticket) {
        this.ticket = ticket;
    }

    // Getter
    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
