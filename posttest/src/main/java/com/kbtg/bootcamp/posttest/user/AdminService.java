package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequestDto;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final LotteryRepository lotteryRepository;

    public AdminService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public String createLottery(LotteryRequestDto requestDto){
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.getTicket());
        lottery.setPrice(requestDto.getPrice());
        lottery.setAmount(requestDto.getAmount());
        lotteryRepository.save(lottery);
        return lottery.getTicket();
    }


}
