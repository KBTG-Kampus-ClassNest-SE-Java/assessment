package com.kbtg.bootcamp.posttest.lottery.rest.dto;

import java.util.List;

public class LotteryListResDto {
    private List<String> ticket;

    public LotteryListResDto() {
    }

    public LotteryListResDto(List<String> ticket) {
        this.ticket = ticket;
    }

    public List<String> getTicket() {
        return ticket;
    }

    public void setTicket(List<String> ticket) {
        this.ticket = ticket;
    }
}
