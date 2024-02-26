package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.Respone.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.request.AdminLotteryRequest;
import org.springframework.http.ResponseEntity;

public interface LotteryService {

    ResponseEntity<Object> addLottery(AdminLotteryRequest lotteryRequest);

    ResponseEntity<LotteryResponse> getAllLotteries();

}
