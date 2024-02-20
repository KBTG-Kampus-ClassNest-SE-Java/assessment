// UserLotteryResponse.java
package com.kbtg.bootcamp.posttest.userLottery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserLotteryResponse {
    private List<String> tickets;
    private int count;
    private int cost;
}
