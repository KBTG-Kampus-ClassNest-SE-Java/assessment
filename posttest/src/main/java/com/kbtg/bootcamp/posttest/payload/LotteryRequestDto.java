package com.kbtg.bootcamp.posttest.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LotteryRequestDto(

        @NotNull
            @Size(min = 6, max = 6 , message = "Ticket length should be 6 characters")
            String ticket,
            @NotNull
            Integer price,
            @NotNull
            Integer amount
        ) {

    @Override
    public String ticket() {
        return ticket;
    }

    @Override
    public Integer price() {
        return price;
    }

    @Override
    public Integer amount() {
        return amount;
    }
}
