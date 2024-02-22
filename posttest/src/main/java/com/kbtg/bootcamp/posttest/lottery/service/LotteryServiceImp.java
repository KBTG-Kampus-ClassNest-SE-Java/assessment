package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.controller.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.entity.LotteryClass;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryServiceImp implements LotteryService {

    @Autowired
    private final LotteryRepo lotteryRepo;

    public LotteryServiceImp(LotteryRepo lotteryRepo) {
        this.lotteryRepo = lotteryRepo;
    }

    @Override
    public List<String> listAllLotteries() {
        return lotteryRepo.findAll().stream().map(LotteryClass::getTicket).collect(Collectors.toList());
    }

    @Override
    public Optional<LotteryClass> createLottery(int id) {
        return Optional.empty();
    }
}
