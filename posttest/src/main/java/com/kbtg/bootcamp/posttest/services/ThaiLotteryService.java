package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.models.LotteriesResponse;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
import com.kbtg.bootcamp.posttest.models.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.repositories.UserRepository;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThaiLotteryService implements LotteryService {
    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Override
    public LotteryResponse addLottery(Lottery lottery) {
        lottery.setCurrentAmount(lottery.getAmount());

        lotteryRepository.save(lottery);

        return new LotteryResponse(lottery.getTicket());
    }

    @Override
    public LotteriesResponse listLotteries() {
        var tickets = lotteryRepository.findDistinctTickets();

        return new LotteriesResponse(tickets);
    }

    @Override
    @Transactional
    public BuyLotteryResponse buyLottery(Integer userId, Integer ticketId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var lottery = lotteryRepository.findById(ticketId).orElseThrow(() -> new LotteryNotFoundException("Lottery not found"));

        if (lottery.getCurrentAmount() == 0) {
            throw new LotterySoldOutException("Lottery is sold out");
        }

        var userTicket = new UserTicket(user, lottery);

        userTicketRepository.save(userTicket);

        lottery.setCurrentAmount(lottery.getCurrentAmount() - 1);
        lotteryRepository.save(lottery);

        return new BuyLotteryResponse(userTicket.getId());
    }

    @Override
    @Transactional
    public LotteryResponse sellBackMyLottery(Integer userId, Integer ticketId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var lottery = lotteryRepository.findById(ticketId).orElseThrow(() -> new LotteryNotFoundException("Lottery not found"));
        var userTicket = userTicketRepository.findFirstByUserUserIdAndLotteryId(user.getUserId(), lottery.getId());

        userTicketRepository.delete(userTicket);

        lottery.setCurrentAmount(lottery.getCurrentAmount() + 1);
        lotteryRepository.save(lottery);

        return new LotteryResponse(lottery.getTicket());
    }
}
