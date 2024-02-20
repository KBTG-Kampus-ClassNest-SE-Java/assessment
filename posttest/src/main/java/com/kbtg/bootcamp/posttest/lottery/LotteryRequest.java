// LotteryRequest.java
package com.kbtg.bootcamp.posttest.lottery;

import lombok.Data;

@Data
public class LotteryRequest {
    private String ticket;
    private Integer price;
    private Integer amount;
}
