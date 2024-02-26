package com.kbtg.bootcamp.posttest.lottery.request;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import jakarta.validation.constraints.Min;

public record AdminLotteryRequest(String ticket,
                                  @Min(value = 1,message = "The number of bags must be greater than 0") int price,
                                  @Min(value = 1,message = "The number of bags must be greater than 0") int amount) {

    public Lottery toLottery() {
        Lottery lottery = new Lottery();
        lottery.setTicket(ticket);
        lottery.setPrice(price);
        lottery.setAmount(amount);
        return lottery;
    }
}
