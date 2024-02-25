// LotteryResponse.java
package com.kbtg.bootcamp.posttest.lottery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
    public class LotteryResponse {
        private List<String> ticket;

    public List<String> getLotteryIds() {
        return ticket;
    }
}
