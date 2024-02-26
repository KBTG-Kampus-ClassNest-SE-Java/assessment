package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.exceptions.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public UserTicketServiceImpl(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    @Override
    public GetLotteriesByUserIdResponse getLotteriesByUserId(String userId) {
        List<UserTicket> userTickets = this.userTicketRepository.findByUserId(userId);

        List<String> ticketIds = new ArrayList<>();
        int sum = 0;
        for (UserTicket t : userTickets) {
            sum += t.getAmount() * t.getLottery().getPrice();

            for (int i = 0; i < t.getAmount(); i++) {
                ticketIds.add(t.getLottery().getTicket());
            }
        }

        int count = ticketIds.size();

        return new GetLotteriesByUserIdResponse(ticketIds, count, sum);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserTicket buyLottery(String userId, String ticketId) {
        Optional<Lottery> optionalLottery = this.lotteryRepository.findById(ticketId);
        if (optionalLottery.isEmpty()) {
            throw new LotteryNotFoundException("Lottery with id " + ticketId + " not found");
        }

        Lottery lottery = optionalLottery.get();
        if (lottery.getAmount() <= 0) {
            throw new LotterySoldOutException("Lottery with id " + ticketId + " is sold out");
        }

        this.lotteryRepository.updateTicketAmountInStore(ticketId, -1);

        UserTicket userTicket;
        if (this.userTicketRepository.existsByUserIdAndTicketId(userId, ticketId)) {
            userTicket = this.userTicketRepository.updateTicketAmountOfUser(userId, ticketId, 1);
        } else {
            userTicket = UserTicket
                    .builder()
                    .userId(userId)
                    .amount(1)
                    .lottery(lottery)
                    .build();
            userTicket = this.userTicketRepository.save(userTicket);
        }

        return userTicket;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String sellLottery(String userId, String ticketId) {
        Optional<UserTicket> optionalUserTicket = this.userTicketRepository.findByUserIdAndTicketId(userId, ticketId);
        if (optionalUserTicket.isEmpty()) {
            throw new UserTicketNotFoundException("User ticket with id " + ticketId + " not found");
        }

        UserTicket userTicket = optionalUserTicket.get();

        this.lotteryRepository.updateTicketAmountInStore(ticketId, userTicket.getAmount());
        this.userTicketRepository.deleteByUserIdAndTicketId(userId, ticketId);

        return ticketId;
    }

}
