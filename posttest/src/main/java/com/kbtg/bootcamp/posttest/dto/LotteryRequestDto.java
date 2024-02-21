package com.kbtg.bootcamp.posttest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LotteryRequestDto {

    private String tickets;

    private int price;

    private int amount;


}
