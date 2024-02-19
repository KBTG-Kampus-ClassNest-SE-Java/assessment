package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.responese.LotteryListResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService {

  private final LotteryRepository lotteryRepository;


  public LotteryServiceImpl(LotteryRepository lotteryRepository) {
    this.lotteryRepository = lotteryRepository;
  }


  @Override
  public LotteryListResponse getLotteries() {
    List<String> tickets = lotteryRepository.findAll().stream()
        .map(Lottery::getTicket)
        .collect(Collectors.toList());
    return new LotteryListResponse(tickets);
  }

}
