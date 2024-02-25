package com.kbtg.bootcamp.posttest.lottery.rest.dto;

import java.util.List;

public class LotteryListResDto {
    private List<String> tickets;

    public LotteryListResDto() {
    }

    public LotteryListResDto(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }
}
