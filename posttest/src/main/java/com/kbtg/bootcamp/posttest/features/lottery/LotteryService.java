package com.kbtg.bootcamp.posttest.features.lottery;

import com.kbtg.bootcamp.posttest.features.lottery.model.get_all_lottery.GetAllLotteryResDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public GetAllLotteryResDto getAllLotteries() {
        List<String> lotteries = this.lotteryRepository.findAllAvailableLottery();

        return new GetAllLotteryResDto(
                lotteries
        );
    }
}
