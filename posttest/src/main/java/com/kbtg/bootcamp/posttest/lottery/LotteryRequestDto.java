package com.kbtg.bootcamp.posttest.lottery;

public record LotteryRequestDto(String ticket, Integer amount, Float price) {
    @Override
    public String ticket() {
        return ticket;
    }

    @Override
    public Integer amount() {
        return amount;
    }

    @Override
    public Float price() {
        return price;
    }
}
