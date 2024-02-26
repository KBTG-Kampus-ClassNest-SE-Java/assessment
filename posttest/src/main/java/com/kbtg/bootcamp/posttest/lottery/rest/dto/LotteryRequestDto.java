package com.kbtg.bootcamp.posttest.lottery.rest.dto;

public class LotteryRequestDto {

    private String ticket;
    private Integer amount;
    private Integer price;


    public LotteryRequestDto() {
    }

    public LotteryRequestDto(String ticket, Integer amount, Integer price) {
        this.ticket = ticket;
        this.amount = amount;
        this.price = price;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
