// LotteryRequest.java
package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LotteryRequest {
    @NotNull
    @Pattern(regexp = "[0-9]{6}", message = "Ticket must be exactly 6 digits")
    private String ticket;

    @NotNull
    @Min(value = 1,message = "price can not be negative ")
    @Max(value = 80, message = "price can not be over 80")
    private Integer price;

    @NotNull
    @Min(value = 1,message = "price can not be negative ")
    @Max(value = 80, message = "price can not be over 80")
    private Integer amount;

    public LotteryRequest(String testLottery, Integer amountTest, Integer priceTest) {

    }
}
