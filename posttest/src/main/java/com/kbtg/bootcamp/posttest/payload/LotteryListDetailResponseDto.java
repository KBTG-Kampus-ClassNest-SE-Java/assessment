package com.kbtg.bootcamp.posttest.payload;

import java.util.List;

public record LotteryListDetailResponseDto(List<String> tickets, Integer count, Integer cost) {}
