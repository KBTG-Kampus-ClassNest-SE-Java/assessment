package com.example.ptts.service;


import com.example.ptts.entity.*;
import com.example.ptts.repository.LotteryRepository;
import com.example.ptts.repository.UserTicketRepository;
import com.example.ptts.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    private final UsersRepository usersRepository;

    private final UserTicketRepository userTicketRepository;

    public ResponseEntity<LotteryResponseDto> findAllLotteries(){
        List<Lottery> lotteries = lotteryRepository.findAll();
        List<String> tickets = lotteries.stream()
                .map(Lottery::getTicketNumber)
                .toList();

        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        return ResponseEntity.ok().body(responseDto);

    }
    public ResponseEntity<UserTicketResponseDto> buyLottery(Integer userId, String ticketNumber){
        Users user = usersRepository.findById(userId);

        Lottery lottery = lotteryRepository.findByTicketNumber(ticketNumber);
        UserTicket userTicket = new UserTicket(user,lottery,1);
        userTicketRepository.save(userTicket);
        UserTicketResponseDto responseDto = new UserTicketResponseDto(userTicket.getId());
        return ResponseEntity.ok().body(responseDto);

    }
    public ResponseEntity<LotteryUserResponseDto> findLotteryByUserId(Integer userId){
        Users users = usersRepository.findById(userId);

        Optional<List<Lottery>> list = userTicketRepository.findDistinctLotteriesByUserId(users.getId());
        List<String> ticket = list.get().stream()
                .map(Lottery::getTicketNumber)
                .toList();

        int count = list.stream().flatMap(Collection::stream).mapToInt(Lottery::getAmount).sum();
        int cost = list.stream().flatMap(Collection::stream).mapToInt(Lottery::getPrice).sum();

        LotteryUserResponseDto responseDto = new LotteryUserResponseDto(ticket, count, cost);
        return ResponseEntity.ok().body(responseDto);
    }

}
