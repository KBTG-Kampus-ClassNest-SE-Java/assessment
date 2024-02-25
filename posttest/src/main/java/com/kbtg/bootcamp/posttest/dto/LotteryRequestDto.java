package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LotteryRequestDto {

    @Size(min = 6 , max = 6)
    @Pattern(regexp = "^[0-9]+$", message = "Invalid ticket")
    private String tickets;

    @Positive(message="Price must be positive")
    @Digits(integer=10, fraction=0, message="Invalid price")
    private int price;

    @Positive(message="Price must be positive")
    @Digits(integer=10, fraction=0, message="Invalid amount")
    private int amount;


}
