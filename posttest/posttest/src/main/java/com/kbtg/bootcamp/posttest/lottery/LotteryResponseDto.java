package com.kbtg.bootcamp.posttest.lottery;

public record LotteryResponseDto(String ticket) {

    @Override
    public String ticket() {
        return ticket;
    }
}
