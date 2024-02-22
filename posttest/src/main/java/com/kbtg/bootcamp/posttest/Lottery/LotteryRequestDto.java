package com.kbtg.bootcamp.posttest.Lottery;

import com.kbtg.bootcamp.posttest.Exception.BadRequestException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LotteryRequestDto(String ticket, Long amount, Double price) {

    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[0-9]{6}$")
    public String ticket() {
        return ticket;
    }

    public Long amount() {
        return amount;
    }

    public Double price() {
        return price;
    }

    public void validate() {
        if (ticket == null || ticket.length() != 6 || !ticket.matches("\\d+")) {
            throw new BadRequestException("Invalid ticket: " + ticket);
        }
        if (amount == null || amount < 0 ) {
            throw new BadRequestException("Invalid amount: " + amount);
        }
        if (price == null || price < 0) {
            throw new BadRequestException("Invalid price: " + price);
        }
    }
}
