package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.core.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.core.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.core.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.user.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.user.UserRepository;
import com.kbtg.bootcamp.posttest.user.UserTicket;
import com.kbtg.bootcamp.posttest.user.UserTicketRepository;
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
    public void addLottery(Lottery lottery) {
        lottery.setCurrentAmount(lottery.getAmount());

        lotteryRepository.save(lottery);
    }

    @Override
    public LotteryResponse listLotteries() {
        var tickets = lotteryRepository.findDistinctTickets();

        return new LotteryResponse(tickets);
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
    public Lottery sellBackMyLottery(Integer userId, Integer ticketId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var lottery = lotteryRepository.findById(ticketId).orElseThrow(() -> new LotteryNotFoundException("Lottery not found"));
        var userTicket = userTicketRepository.findFirstByUserUserIdAndLotteryId(user.getUserId(), lottery.getId());

        userTicketRepository.delete(userTicket);

        lottery.setCurrentAmount(lottery.getCurrentAmount() + 1);
        lotteryRepository.save(lottery);

        return lottery;
    }
}
