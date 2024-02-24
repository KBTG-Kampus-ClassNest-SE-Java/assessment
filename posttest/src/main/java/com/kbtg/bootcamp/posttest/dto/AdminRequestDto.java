package com.kbtg.bootcamp.posttest.dto;

public class AdminRequestDto {

    private String ticket;

    private Integer price;

    private Integer amount;

    public AdminRequestDto(String ticket, Integer price, Integer amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
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
