package com.kbtg.bootcamp.posttest.userTicket;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.user.User;
import com.kbtg.bootcamp.posttest.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTicketService {

    private final UserTicketRepository userTicketRepository;
    private final LotteryRepository lotteryRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserTicketService(UserTicketRepository userTicketRepository, LotteryRepository lotteryRepository, UserRepository userRepository) {
        this.userTicketRepository = userTicketRepository;
        this.lotteryRepository = lotteryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserTicketResponse buyLottery(String userId, String lottery_id) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        userRepository.save(user);

        Lottery lottery = lotteryRepository.findById(Long.valueOf(lottery_id))
                .orElseThrow(() -> new NotFoundException("Lottery not found with ID: " + lottery_id));
        lotteryRepository.save(lottery);

        UserTicket userTicket = new UserTicket(user.getUser_id(), lottery.getLottery_id());
        userTicketRepository.save(userTicket);

        String ticketId = String.valueOf(userTicket.getTicket_id());
        return new UserTicketResponse(Collections.singletonList(ticketId));

    }

    public UserTicketResponse deleteLotteryLotteryById(String userId, String ticketId) {
        UserTicket userTicket = userTicketRepository
                .findByUserIdAndLotteryId(Long.valueOf(userId), Long.valueOf(ticketId));
        if (userTicket != null) {
            userTicketRepository.delete(userTicket);
            return new UserTicketResponse(Collections.singletonList(ticketId));
        } else {
            throw new NotFoundException("Lottery ticket not found for user " + userId + " and ticket ID " + ticketId);
        }
    }


    public UserTicketResponse showUserLotteriesList(String userId) {
        Long userIdLong = Long.parseLong(userId);

        List<UserTicket> userTickets = userTicketRepository.findByUserId(userIdLong);

        List<String> ticketIds = userTickets.stream()
                .map(UserTicket::getLotteryId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        return new UserTicketResponse(ticketIds);
    }

}

