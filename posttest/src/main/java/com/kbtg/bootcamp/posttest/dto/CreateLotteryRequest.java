package com.kbtg.bootcamp.posttest.dto;

public record CreateLotteryRequest(
        String ticket,
        Integer price,
        Integer amount
) {
}
