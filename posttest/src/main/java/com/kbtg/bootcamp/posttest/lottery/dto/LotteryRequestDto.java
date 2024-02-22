package com.kbtg.bootcamp.posttest.lottery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotteryRequestDto {

    @NotNull
    @Size(min = 6, max = 6, message = "lottery ticket should be 6 characters")
    private String ticket;
    @NotNull
    private Integer price;
    @NotNull
    private Integer amount;

    public String getTicket() {
        return ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }
}
