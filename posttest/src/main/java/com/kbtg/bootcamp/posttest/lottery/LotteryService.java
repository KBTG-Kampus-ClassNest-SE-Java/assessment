package com.kbtg.bootcamp.posttest.lottery;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    final LotteryRepository lotteryRepository;
    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Transactional
    public Lottery createLottery(LotteryRequestDto requestDto){
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.getTicket());
        lotteryRepository.save(lottery);
        return lottery;
    }

}
