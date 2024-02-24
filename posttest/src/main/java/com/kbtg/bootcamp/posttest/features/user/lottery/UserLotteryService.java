package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.BadRequestException;
import com.kbtg.bootcamp.posttest.features.date.DateTimeProviderService;
import com.kbtg.bootcamp.posttest.features.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.buy_lottery.BuyLotteryResDto;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.get_my_lottery.GetMyLotteryResDto;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.sell_lottery.SellLotteryResDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserLotteryService {

    private final LotteryRepository lotteryRepository;

    private final UserTicketRepository userTicketRepository;

    private final DateTimeProviderService dateTimeProviderService;

    public UserLotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository, DateTimeProviderService dateTimeProviderService) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
        this.dateTimeProviderService = dateTimeProviderService;
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
        userTicket.setBuyDate(dateTimeProviderService.getCurrentDateTime());

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

    public GetMyLotteryResDto getMyLottery(String userId) {
        List<UserTicket> myLotteries = userTicketRepository.findByUserIdOrderByTicketIdAsc(userId);

        final List<String> tickets = myLotteries.stream().map(UserTicket::getTicketId).toList();
        final Integer count = tickets.size();
        final BigDecimal cost = myLotteries.stream().map(UserTicket::getBuyPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new GetMyLotteryResDto(
                tickets,
                count,
                cost
        );
    }

    @Transactional
    public SellLotteryResDto sellLottery(String userId, String ticketId) {
        List<UserTicket> myLotteries = userTicketRepository.findByUserIdAndTicketId(userId, ticketId);

        if (myLotteries.isEmpty()) {
            throw new BadRequestException("Lottery no " + ticketId + " is not found in you inventory");
        }

        userTicketRepository.sellTicket(userId, ticketId);

        return new SellLotteryResDto(
                ticketId
        );
    }
}
