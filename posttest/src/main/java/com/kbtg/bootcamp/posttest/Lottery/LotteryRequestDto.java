package com.kbtg.bootcamp.posttest.Lottery;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import org.antlr.v4.runtime.misc.NotNull;

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
