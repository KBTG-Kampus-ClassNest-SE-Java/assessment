package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.shared.responses.LotteryResponse;
import com.kbtg.bootcamp.posttest.user.BuyLotteryResponse;
import org.springframework.stereotype.Service;

@Service
public interface LotteryService {
    public LotteryResponse addLottery(Lottery lottery);

    public LotteriesResponse listLotteries();

    BuyLotteryResponse buyLottery(Integer userId, Integer ticketId);

    Lottery sellBackMyLottery(Integer userId, Integer ticketId);
}
