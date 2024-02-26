package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryService {

    final LotteryRepository lotteryRepository;
    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public LotteryListResponseDto getAllLotteries() {
        List<String> tickets = lotteryRepository.findAll()
                .stream()
                .filter(lottery -> lottery.getAmount() >= 1)
                .map(Lottery::getTicket)
                .toList();
        return new LotteryListResponseDto(tickets);
    }

    public LotteryResponseDto createLottery (LotteryRequestDto requestDto){
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.getTicket());
        lottery.setAmount(requestDto.getAmount());
        lottery.setPrice(requestDto.getPrice());
        lotteryRepository.save(lottery);
        return new LotteryResponseDto(lottery.getTicket());
    }

}
