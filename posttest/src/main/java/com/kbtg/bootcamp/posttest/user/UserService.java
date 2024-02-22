package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final LotteryRepository lotteryRepository;

    public UserService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public LotteryListResponseDto getAllLotteries() {
        List<String> tickets = lotteryRepository.findAll()
                                                .stream()
                                                .map(Lottery::getTicket)
                                                .toList();
        return new LotteryListResponseDto(tickets);
    }


}
