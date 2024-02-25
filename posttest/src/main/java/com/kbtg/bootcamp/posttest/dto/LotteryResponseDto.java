package com.kbtg.bootcamp.posttest.dto;

import java.util.List;

public class LotteryResponseDto {
    private List<String> tickets;


    public LotteryResponseDto(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

}
