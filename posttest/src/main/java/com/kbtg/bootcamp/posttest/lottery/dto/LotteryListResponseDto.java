package com.kbtg.bootcamp.posttest.lottery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotteryListResponseDto {

    private List<String> tickets;

}
