package com.kbtg.bootcamp.posttest.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MyLotteryResponse {
    private List<String> tickets;
    private Integer count;
    private BigDecimal cost;

    public MyLotteryResponse(List<String> tickets, Integer count, BigDecimal cost) {
        this.tickets = tickets;
        this.count = count;
        this.cost = cost;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
