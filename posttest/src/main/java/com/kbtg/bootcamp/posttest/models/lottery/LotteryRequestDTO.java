package com.kbtg.bootcamp.posttest.models.lottery;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotteryRequestDTO {

    @Pattern(regexp = "[0-9]{6}", message = "Ticket must be a 6 digits number")
    private String ticket;
    @Positive(message = "Price must be positive")
    private Double price;
    @Positive(message = "Amount must be positive")
    private Integer amount;

    public LotteryRequestDTO(String ticket, Double price, Integer amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }
}
