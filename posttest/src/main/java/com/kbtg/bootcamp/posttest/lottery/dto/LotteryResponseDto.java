package com.kbtg.bootcamp.posttest.lottery.dto;

import java.util.List;

public class LotteryResponseDto {

    private String ticket;

    public LotteryResponseDto() {

    }

    public LotteryResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
