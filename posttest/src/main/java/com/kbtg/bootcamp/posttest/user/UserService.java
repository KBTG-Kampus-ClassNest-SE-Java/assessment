package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final LotteryRepository lotteryRepository;

    public UserService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public List<Lottery> getLottery(){
        List<Lottery> lotteries = lotteryRepository.findAll();
        return lotteries;
    }
}
