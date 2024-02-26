package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.models.LotteriesResponse;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
import com.kbtg.bootcamp.posttest.models.BuyLotteryResponse;
import org.springframework.stereotype.Service;

@Service
public interface LotteryService {
    LotteryResponse addLottery(Lottery lottery);

    LotteriesResponse listLotteries();

    BuyLotteryResponse buyLottery(Integer userId, Integer ticketId);

    LotteryResponse sellBackMyLottery(Integer userId, Integer ticketId);
}
