package com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery;

import com.kbtg.bootcamp.posttest.validator.lottery_id.IsLotteryId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateLotteryReqDto(
        @NotNull
        @IsLotteryId
        String ticket,

        @NotNull
        @Positive
        @Min(1)
        BigDecimal price,

        @NotNull
        @Positive
        @Min(1)
        Integer amount
) {
}
