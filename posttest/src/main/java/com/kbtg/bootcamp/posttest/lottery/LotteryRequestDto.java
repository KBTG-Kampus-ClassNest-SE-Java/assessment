package com.kbtg.bootcamp.posttest.lottery;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class LotteryRequestDto {

    @NotNull
//    @Size(min = 6, max = 6, message = "Ticket length must contains 6 characters of numbers")
    @Pattern(regexp = "^\\d{6}$", message = "Ticket must contains only 6 characters of numbers")
    private String ticket;
    private Integer price;
    private  Integer amount;

    public LotteryRequestDto() {

    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
