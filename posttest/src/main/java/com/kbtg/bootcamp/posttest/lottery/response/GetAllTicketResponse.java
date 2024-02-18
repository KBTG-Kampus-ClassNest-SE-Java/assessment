package com.kbtg.bootcamp.posttest.lottery.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTicketResponse {

  private List<String> tickets;
  private int count;
  private int cost;
}
