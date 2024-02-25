package com.kbtg.bootcamp.posttest.Lottery;

import jakarta.validation.constraints.*;

public record LotteryRequestDto(@NotNull @NotBlank @Size(min = 6, max = 6) @Pattern(regexp = "^[0-9]{6}$")
                                String ticket,
                                @Min(value = 0)
                                Long amount,
                                @Min(value = 0)
                                Double price) {

    public String ticket() {
        return ticket;
    }

    public Long amount() {
        return amount;
    }

    public Double price() {
        return price;
    }
}
