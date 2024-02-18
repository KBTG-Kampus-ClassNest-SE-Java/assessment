package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.repository.UserTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTicketService {

    private final UserTicketRepository userTicketRepository;
    private final LotteryRepository lotteryRepository;
    private final UserRepository userRepository;

    public UserTicketService(UserTicketRepository userTicketRepository, LotteryRepository lotteryRepository, UserRepository userRepository) {
        this.userTicketRepository = userTicketRepository;
        this.lotteryRepository = lotteryRepository;
        this.userRepository = userRepository;
    }

    public UserTicketDto buyLotteries(String userId, String ticketId) {
        UserTicket userTicket = new UserTicket();

        User user = userRepository.findById(userId).get();
        user.setUserId(userId);

        Lottery lottery = lotteryRepository.findById(ticketId).get();
        lottery.setTicket(ticketId);

        userTicket.setUser(user);
        userTicket.setLottery(lottery);

        UserTicket saved = userTicketRepository.save(userTicket);
        return new UserTicketDto(saved.getId());
    }

    public UserTickerSummaryDto getLotteriesByUserId(String userId) {
        UserTickerSummaryDto userTickerSummaryDto = new UserTickerSummaryDto();

        User user = userRepository.findById(userId).get();
        user.setUserId(userId);

        List<UserTicket> byUser = userTicketRepository.findByUser(user);
        List<String> tickets = byUser.stream().map(b -> b.getLottery().getTicket()).collect(Collectors.toList());

        userTickerSummaryDto.setTickets(tickets);
        userTickerSummaryDto.setCount(tickets.size());
        userTickerSummaryDto.setCost(calculateTotalPrice(byUser));

        return userTickerSummaryDto;
    }

    private static Integer calculateTotalPrice(List<UserTicket> byUser) {
        Integer sum = 0;
        for(UserTicket ticket : byUser) {
            sum += ticket.getLottery().getPrice();
        }
        return sum;
    }

}
