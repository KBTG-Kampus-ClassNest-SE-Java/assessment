package com.kbtg.bootcamp.posttest.lottery;

//import jakarta.transaction.Transactional;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    final LotteryRepository lotteryRepository;
    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public LotteryResponseDto createLottery(LotteryRequestDto requestDto){
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.getTicket());
        lottery.setAmount(requestDto.getAmount());
        lottery.setPrice(requestDto.getPrice());
        lotteryRepository.save(lottery);
        return new LotteryResponseDto(lottery.getTicket());
    }

}
