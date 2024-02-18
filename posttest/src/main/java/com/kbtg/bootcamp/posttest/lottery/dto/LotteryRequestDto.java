package com.kbtg.bootcamp.posttest.lottery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotteryRequestDto {
    private String ticket;
    private Integer price;
    private Integer amount;
}
