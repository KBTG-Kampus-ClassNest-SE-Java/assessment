package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.User;
import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepository lotteryRepository;

    @Override
    public Lottery addLottery(Lottery lottery) {
        return lotteryRepository.save(lottery);
    }

    @Override
    public List<Lottery> getLotteries() {
        return lotteryRepository.findAll();
    }

}
