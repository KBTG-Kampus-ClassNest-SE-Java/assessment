package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public LotteryServiceImpl(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Lottery createLottery(CreateLotteryRequest createLotteryRequest) {
        Lottery lottery = Lottery.builder()
                .ticket(createLotteryRequest.ticket())
                .price(createLotteryRequest.price())
                .amount(createLotteryRequest.amount())
                .build();

        this.lotteryRepository.save(lottery);

        return lottery;
    }

    @Override
    public List<Lottery> getLotteries() {
        return this.lotteryRepository.findAll();
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

}
