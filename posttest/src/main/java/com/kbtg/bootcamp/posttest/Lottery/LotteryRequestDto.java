package com.kbtg.bootcamp.posttest.Lottery;

import com.kbtg.bootcamp.posttest.Exception.BadRequestException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
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

    public void validate() {
        if (ticket == null || ticket.length() != 6) {
            throw new BadRequestException("Invalid ticket: " + ticket);
        }
        if (amount == null || amount < 0 || amount != Math.floor(amount)) {
            throw new BadRequestException("Invalid amount: " + amount);
        }
        if (price == null || price < 0) {
            throw new BadRequestException("Invalid price: " + price);
        }
    }
}
