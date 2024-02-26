package com.kbtg.bootcamp.posttest.user;

import lombok.Data;

@Data
public class BuyLotteryResponse {
    private Integer id;

    public BuyLotteryResponse(Integer id) {
        this.id = id;
    }
}
