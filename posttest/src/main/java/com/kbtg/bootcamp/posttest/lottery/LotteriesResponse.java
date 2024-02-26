package com.kbtg.bootcamp.posttest.lottery;

import lombok.Data;

import java.util.List;

@Data
public class LotteriesResponse {
    private List<String> tickets;

    public LotteriesResponse(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }
}
