package com.kbtg.bootcamp.posttest.admin.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLotteryRequest {


    @Pattern(regexp = "\\d+", message = "ticket must be numeric")
    @NotEmpty(message = "ticket is required")
    @Size(min = 6, max = 6, message = "ticket must be 6 digits")
    private String ticket;

    @NotNull(message = "price is required")
    @Digits(integer = 10, fraction = 0, message = "price must be a number")
    private Integer price;

    @NotNull(message = "amount is required")
    @Digits(integer = 10, fraction = 0, message = "amount must be a number")
    private Integer amount;}


