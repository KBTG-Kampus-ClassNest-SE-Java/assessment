package com.kbtg.bootcamp.posttest.lottery;

import lombok.Data;

import java.util.List;

@Data
public class LotteryResponse {
    private List<String> tickets;

    public LotteryResponse(List<String> tickets) {
        this.tickets = tickets;
    }
}
