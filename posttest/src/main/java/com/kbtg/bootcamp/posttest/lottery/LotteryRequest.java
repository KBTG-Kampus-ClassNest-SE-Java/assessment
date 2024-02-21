// LotteryRequest.java
package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.*;
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
}
