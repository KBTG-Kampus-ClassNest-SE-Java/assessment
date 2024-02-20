package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.BadRequestException;
import com.kbtg.bootcamp.posttest.features.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.buy_lottery.BuyLotteryResDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserLotteryService {

    private final LotteryRepository lotteryRepository;

    private final UserTicketRepository userTicketRepository;

    public UserLotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public BuyLotteryResDto buy(String userId, String ticketId) {
        List<Lottery> availableLotteries = lotteryRepository.findAvailableLotteryByTicketId(ticketId);

        if (availableLotteries.isEmpty()) {
            throw new BadRequestException("Lottery no " + ticketId + " is out of stock");
        }

        Lottery cheapestLottery = availableLotteries.get(0);

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setBuyPrice(cheapestLottery.getPrice());
        userTicket.setTicketId(ticketId);
        userTicket.setBuyDate(LocalDateTime.now());

        UserTicket savedUserTicket = userTicketRepository.save(userTicket);

        int newAmount = cheapestLottery.getAmount() - 1;
        cheapestLottery.setAmount(newAmount);

        if (newAmount == 0) {
            lotteryRepository.delete(cheapestLottery);
        } else {
            lotteryRepository.save(cheapestLottery);
        }

        return new BuyLotteryResDto(
                savedUserTicket.getId().toString()
        );
    }
}
