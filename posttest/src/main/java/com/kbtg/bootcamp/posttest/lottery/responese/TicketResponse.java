package com.kbtg.bootcamp.posttest.lottery.responese;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
  private String ticket;
}
