package com.kbtg.bootcamp.posttest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LotteryResponseDto {

    private List<String> tickets;
}
