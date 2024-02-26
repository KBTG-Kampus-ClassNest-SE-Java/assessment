package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.core.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.user.*;
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
        lotteryRepository.save(lottery);
    }

    @Override
    public LotteryResponse listLotteries() {
        var tickets = lotteryRepository.findDistinctTickets();

        return new LotteryResponse(tickets);
    }

    @Override
    public ButLotteryResponse buyLottery(Integer userId, Integer ticketId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var lottery = lotteryRepository.findById(ticketId).orElseThrow(() -> new UserNotFoundException("Lottery not found"));
        var userTicket = new UserTicket(user, lottery);

        userTicketRepository.save(userTicket);

        return new ButLotteryResponse(userTicket.getId());
    }
}
