package com.kbtg.bootcamp.posttest.payload;

//public record LotteryResponseDto(String ticket) {}

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class LotteryResponseDto {
    private String ticket;

    public LotteryResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public LotteryResponseDto() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
