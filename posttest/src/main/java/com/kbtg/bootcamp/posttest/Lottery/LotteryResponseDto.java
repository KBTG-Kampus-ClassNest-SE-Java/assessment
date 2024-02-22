package com.kbtg.bootcamp.posttest.Lottery;

public record LotteryResponseDto(String ticket) {

    @Override
    public String ticket() {
        return ticket;
    }
}
