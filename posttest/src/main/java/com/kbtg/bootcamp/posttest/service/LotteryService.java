package com.kbtg.bootcamp.posttest.service


import com.kbtg.bootcamp.posttest.entity.*;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.repository.UsersRepository;
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
    public ResponseEntity<LotteryResponseDto> deleteTicketByUserId(Integer userId, String ticketNumber){
        Users users = usersRepository.findById(userId);
        Lottery lottery = lotteryRepository.findByTicketNumber(ticketNumber);


        List<String> tickets = Arrays.asList(lottery.getTicketNumber());
        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        userTicketRepository.deleteUserTicketByUserIdAndTicketId(users.getId(), lottery.getId());
        return ResponseEntity.ok().body(responseDto);
    }
    public ResponseEntity<LotteryResponseDto> createLottery(LotteryRequestDto requestDto) throws Exception{

        Lottery lottery = new Lottery(requestDto.getTickets(), requestDto.getPrice(), requestDto.getAmount());
        lotteryRepository.save(lottery);

        List<String> tickets = Arrays.asList(lottery.getTicketNumber());
        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        return ResponseEntity.status(201).body(responseDto);
    }

}
