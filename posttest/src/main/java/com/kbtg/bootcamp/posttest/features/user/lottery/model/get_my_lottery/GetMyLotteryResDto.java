package com.kbtg.bootcamp.posttest.features.user.lottery.model.get_my_lottery;

import java.math.BigDecimal;
import java.util.List;

public record GetMyLotteryResDto(
        List<String> tickets,
        Integer count,
        BigDecimal cost
) {
}
