// LotteryListResponse.java
package com.kbtg.bootcamp.posttest.lottery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class LotteryListResponse {
    private List<String> tickets;

}
