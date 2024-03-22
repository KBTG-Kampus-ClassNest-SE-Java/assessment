package com.kbtg.bootcamp.posttest.payload;

import java.util.List;

public record LotteryDetailDto(List<String> tickets, Integer count, Integer cost) {
}
