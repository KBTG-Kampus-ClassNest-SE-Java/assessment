package com.kbtg.bootcamp.posttest.lottery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class LotteryListResponseDto {
    @JsonProperty("tickets")
    private List<String> tickets;

    public LotteryListResponseDto(List<String> tickets) {
        this.tickets = tickets;
    }
}
