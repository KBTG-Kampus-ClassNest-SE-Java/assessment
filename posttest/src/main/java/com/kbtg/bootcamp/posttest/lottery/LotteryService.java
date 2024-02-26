package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.user.BuyLotteryResponse;
import org.springframework.stereotype.Service;

@Service
public interface LotteryService {
    public void addLottery(Lottery lottery);
    public LotteryResponse listLotteries();
    BuyLotteryResponse buyLottery(Integer userId, Integer ticketId);
}
