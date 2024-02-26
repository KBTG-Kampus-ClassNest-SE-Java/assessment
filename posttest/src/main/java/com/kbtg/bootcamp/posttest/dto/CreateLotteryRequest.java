package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.*;

public record CreateLotteryRequest(
        @Pattern(regexp = "^[0-9]{6}$", message = "Ticket must be 6 digits.")
        @NotNull
        String ticket,

        @Min(value = 0, message = "Price must be greater than or equal to 0.")
        @NotNull
        Integer price,

        @Min(value = 0, message = "Amount must be greater than or equal to 0.")
        @NotNull
        Integer amount
) {
}
