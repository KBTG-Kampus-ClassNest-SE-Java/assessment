package com.kbtg.bootcamp.posttest.features.lottery.admin;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.features.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryReqDto;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryResDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AdminLotteryService {

    private final LotteryRepository lotteryRepository;

    public AdminLotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Transactional
    public CreateLotteryResDto createLottery(CreateLotteryReqDto req) {
        final String ticketId = req.ticket();
        final BigDecimal price = req.price();
        final int amount = req.amount();

        Lottery lottery = lotteryRepository.findByTicketIdAndPrice(ticketId, price)
                .orElseGet(Lottery::new);

        final int currentAmount = lottery.getAmount() != null ? lottery.getAmount() : 0;
        final int newAmount = currentAmount + amount;

        lottery.setTicketId(ticketId);
        lottery.setAmount(newAmount);
        lottery.setPrice(price);

        lotteryRepository.save(lottery);

        return new CreateLotteryResDto(
                ticketId
        );
    }
}
