package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LotteryStoreServiceImpl implements LotteryStoreService {

    private final LotteryRepository lotteryRepository;

    public LotteryStoreServiceImpl(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Lottery createLottery(CreateLotteryRequest createLotteryRequest) {
        Lottery lottery = Lottery.builder()
                .ticket(createLotteryRequest.ticket())
                .price(createLotteryRequest.price())
                .amount(createLotteryRequest.amount())
                .build();

        this.lotteryRepository.save(lottery);

        return lottery;
    }

    @Override
    public List<Lottery> getAvailableLotteries() {
        return this.lotteryRepository.findByAmountGreaterThan0();
    }

}
