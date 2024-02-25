package com.kbtg.bootcamp.posttest.payload;

import java.util.List;

//public record LotteryListResponseDto(List<String> tickets) {}

public class LotteryListResponseDto {

    private List<String> tickets;

    public LotteryListResponseDto(List<String> tickets) {
        this.tickets = tickets;
    }

    public LotteryListResponseDto() {
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<String> getTickets() {
        return tickets;
    }
}