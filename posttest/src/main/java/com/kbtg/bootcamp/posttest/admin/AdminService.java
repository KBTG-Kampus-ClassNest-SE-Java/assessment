package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    final LotteryRepository lotteryRepository;
    public AdminService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

}
