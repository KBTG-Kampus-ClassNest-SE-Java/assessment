package com.kbtg.bootcamp.posttest.lottery.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LotteryTicketRequest {
    @NotNull(message = "ticket is required")
    @Pattern(regexp = "\\d{6}", message = "ticket number must be 6 digits")
    private String ticket;

    @NotNull(message = "price is required")
    @Min(value = 1, message = "price should not be less than 1")
    private Integer price;

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount should not be less than 1")
    private Integer amount;
}
