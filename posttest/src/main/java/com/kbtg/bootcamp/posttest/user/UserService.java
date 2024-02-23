package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.user.dto.UserTicketsRequestDto;
import org.springframework.stereotype.Service;

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
                .map(Lottery::getTicket)
                .toList();
        return new LotteryListResponseDto(tickets);
    }

//    public UserTicketsRequestDto buyLotteryTicket(Long userId, String ticketId){
//        UserTicket userTicket = new UserTicket();
//        userTicket.setUserId(userId);
//        userTicket.setTicket(ticketId);
//        userTicketRepository.save(userTicket);
//        return new UserTicketsRequestDto();
//    }

}