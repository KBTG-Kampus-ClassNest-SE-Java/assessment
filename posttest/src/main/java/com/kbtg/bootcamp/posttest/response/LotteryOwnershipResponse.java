package com.kbtg.bootcamp.posttest.response;

import java.util.List;


public class LotteryOwnershipResponse {
    private List<String> ticket;
    private int count;
    private int cost;

    public LotteryOwnershipResponse() {
    }

    public LotteryOwnershipResponse(List<String> ticket, int count, int cost) {
        this.ticket = ticket;
        this.count = count;
        this.cost = cost;
    }

    public List<String> getTicket() {
        return ticket;
    }

    public void setTicket(List<String> ticket) {
        this.ticket = ticket;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}


