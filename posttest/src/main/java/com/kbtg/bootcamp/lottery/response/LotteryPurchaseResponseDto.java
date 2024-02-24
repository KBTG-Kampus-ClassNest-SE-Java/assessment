package com.kbtg.bootcamp.lottery.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LotteryPurchaseResponseDto {

    @JsonProperty("id")
    private String ticketId;

    public LotteryPurchaseResponseDto(Integer ticketId) {
        this.ticketId = String.valueOf(ticketId);
    }


    public String getTicketId () {
        return this.ticketId;
    }
}
