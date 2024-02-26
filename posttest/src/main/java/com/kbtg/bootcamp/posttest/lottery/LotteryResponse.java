package com.kbtg.bootcamp.posttest.lottery;

public class LotteryResponse {
    private String ticket;

    public LotteryResponse(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
