// LotteryResponse.java
package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
    public class LotteryResponse {
        private List<String> ticket;


}
