package com.kbtg.bootcamp.posttest.lottery;

public class LotteryRequest {

    private String ticket;
    private Integer amount;
    private Integer price;

    public LotteryRequest() {
    }

    public LotteryRequest(String ticket, Integer amount, Integer price) {
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
