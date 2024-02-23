package com.kbtg.bootcamp.posttest.lottery.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotteryRequestDto {

    @NotNull(message = "Lottery ticket must not be null")
    @Size(min = 6, max = 6, message = "Lottery ticket should be 6 characters")
    @Pattern(regexp = "\\d+", message = "Lottery ticket should be numbers")
    private String ticket;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    @Min(value = 80, message = "Price should be 80 - 100")
    @Max(value = 100, message = "Price should be 80 - 100")
    private Integer price;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be a positive number")
    @Min(value = 1, message = "increase one lottery at a time")
    @Max(value = 1, message = "increase one lottery at a time")
    private Integer amount;

}
