package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.lottery.request.SellLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.service.UserTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;

    private final LotteryRepository lotteryRepository;

    @Override
    public ResponseEntity<Object> buyLottery(String userId, String ticketId) {

        Optional<Lottery> lotteryOptional = lotteryRepository.findByTicket(ticketId);
        if (lotteryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setLottery(lotteryOptional.get());
        userTicket.setAmount(1);
        userTicket.setCost(lotteryOptional.get().getPrice());
        UserTicket savedUserTicket = userTicketRepository.save(userTicket);
        return new ResponseEntity<>((savedUserTicket), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUserLotteryTickets(String userId) {
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId);

        int totalCost = userTickets.stream()
                .mapToInt(ut -> ut.getLottery().getPrice() * ut.getAmount())
                .sum();

        int totalTickets = userTickets.size();

        List<String> ticketNumbers = userTickets.stream()
                .map(ut -> ut.getLottery().getTicket())
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("tickets", ticketNumbers);
        result.put("count", totalTickets);
        result.put("cost", totalCost);
        return ResponseEntity.ok(result);
    }


    @Transactional
    @Override
    public ResponseEntity<SellLotteryResponse> sellLotteryTicket(String userId, String ticketId) {
        List<UserTicket> userTicket = userTicketRepository.findByUserIdAndLotteryTicket(userId, ticketId);
        List<Lottery> lotteryList = userTicket.stream()
                .map(UserTicket::getLottery)
                .collect(Collectors.toList());
        userTicketRepository.deleteUserTicketByLotteryIn(lotteryList);
        SellLotteryResponse sellLotteryResponse = new SellLotteryResponse(ticketId);
        return ResponseEntity.ok(sellLotteryResponse);
    }
}
