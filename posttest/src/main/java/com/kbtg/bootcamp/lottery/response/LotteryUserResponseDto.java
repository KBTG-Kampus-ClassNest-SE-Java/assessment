package com.kbtg.bootcamp.lottery.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class LotteryUserResponseDto {
    @JsonProperty("tickets")
    private List<String> ticketNumber;

    @JsonProperty("cost")
    private BigDecimal pricePaid;

    @JsonProperty("count")
    private Long totalTicket;



    public LotteryUserResponseDto(List<String> ticketNumbers, BigDecimal totalPrice, Long totalTicket) {
        this.ticketNumber = ticketNumbers;
        this.pricePaid = totalPrice;
        this.totalTicket = totalTicket;
    }
}
