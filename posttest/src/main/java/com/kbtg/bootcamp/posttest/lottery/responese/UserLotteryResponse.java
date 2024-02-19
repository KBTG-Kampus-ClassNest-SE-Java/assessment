package com.kbtg.bootcamp.posttest.lottery.responese;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLotteryResponse {

  private List<String> ticket;

  private int count;

  private int username;

}
