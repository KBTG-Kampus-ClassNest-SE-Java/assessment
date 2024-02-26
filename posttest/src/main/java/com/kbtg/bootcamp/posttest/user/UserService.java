package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.user.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.user.dto.UserTicketsRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public UserService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public LotteryListResponseDto getAllLotteries() {
        List<String> tickets = lotteryRepository.findAll()
                .stream()
                .filter(lottery -> lottery.getAmount() >= 1)
                .map(Lottery::getTicket)
                .toList();
        return new LotteryListResponseDto(tickets);
    }

    public UserTicketsRequestDto buyLotteryTicket(String userId, String lotteries) {
        Optional<Lottery> ticket = lotteryRepository.findByTicket(String.valueOf(lotteries));

        Lottery lottery = ticket.orElseThrow(() -> new NotFoundException("Lottery Unavailable Exception"));

        if (lottery.getAmount() <= 0) {
            throw new NotFoundException("No available tickets for this lottery");
        }

        lottery.setAmount(lottery.getAmount() - 1);
        lotteryRepository.save(lottery);

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(String.valueOf(userId));
        userTicket.setTicket(lotteries);
        userTicket.setAmount(lottery.getAmount() + 1);
        userTicket.setPrice(lottery.getPrice());
        userTicketRepository.save(userTicket);

        return new UserTicketsRequestDto(userId);

    }

    public UserTicketResponseDto getUserLottery(String userId) {
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId);

        if (userTickets == null || userTickets.isEmpty()) {
            throw new NotFoundException("No available user tickets for this user Id");
        }

        double totalAmount = 0.0;
        double totalPrice = 0.0;

        List<String> tickets = new ArrayList<>();

        for (UserTicket userTicket : userTickets) {
            totalAmount += userTicket.getAmount();
            totalPrice += userTicket.getPrice();
            tickets.add(userTicket.getTicket());
        }

        UserTicketResponseDto userTicketResponseDto = new UserTicketResponseDto();
        userTicketResponseDto.setTickets(tickets);
        userTicketResponseDto.setCount(totalAmount);
        userTicketResponseDto.setTotalPrice(totalPrice);

        return userTicketResponseDto;

    }

    public LotteryResponseDto deleteLottery (String userId, String ticket) {
        UserTicket userTicket = userTicketRepository.findByUserIdAndTicket(userId, ticket);

        if (userTicket == null) {
            throw new NotFoundException("Ticket not found for the user");
        }

        userTicketRepository.delete(userTicket);

        // to resell lottery tickets
        Lottery lottery = new Lottery();
        lottery.setTicket(ticket);
        lottery.setAmount(userTicket.getAmount());
        lottery.setPrice(userTicket.getPrice());
        lotteryRepository.save(lottery);

        return new LotteryResponseDto(ticket);
    }
}