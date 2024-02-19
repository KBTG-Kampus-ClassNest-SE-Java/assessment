package com.kbtg.bootcamp.posttest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateTicketRequestDTO {

    @NotBlank(message = "Lottery number is mandatory")
    @Pattern(regexp = "^[0-9]{6}$",message = "Lottery number must be 6 digit numbers")
    private String ticket;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Lottery price must greater than 0")
    private int price;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount of lottery must greater than 0")
    private int amount;

}
