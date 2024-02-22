package com.kbtg.bootcamp.posttest.Lottery;

public record LotteryRequestDto(String ticket, Long amount, Double price) {
    @Override
    public String ticket() {
        return ticket;
    }

    @Override
    public Long amount() {
        return amount;
    }

    @Override
    public Double price() {
        return price;
    }
}
