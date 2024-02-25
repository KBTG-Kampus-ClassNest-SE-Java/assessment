package com.example.ptts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LotteryResponseDto {

    private List<String> tickets;
}
