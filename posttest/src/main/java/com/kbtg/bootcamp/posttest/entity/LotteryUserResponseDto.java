package com.kbtg.bootcamp.posttest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LotteryUserResponseDto {
    @JsonProperty("tickets")
    private List<String> ticketNumber;

    @JsonProperty("cost")
    private int pricePaid;

    @JsonProperty("count")
    private int totalTicket;


    public LotteryUserResponseDto(List<String> ticketNumbers, int totalPrice, int totalTicket) {
        this.ticketNumber = ticketNumbers;
        this.pricePaid = totalPrice;
        this.totalTicket = totalTicket;
    }
}
