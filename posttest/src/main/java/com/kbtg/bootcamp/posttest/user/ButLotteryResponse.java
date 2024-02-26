package com.kbtg.bootcamp.posttest.user;

import lombok.Data;

@Data
public class ButLotteryResponse {
    private Integer id;

    public ButLotteryResponse(Integer id) {
        this.id = id;
    }
}
