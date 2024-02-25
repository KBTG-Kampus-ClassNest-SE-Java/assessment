package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.model.UserTicket;
import com.kbtg.bootcamp.posttest.model.Users;
import com.kbtg.bootcamp.posttest.repositoty.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositoty.UserTicketRepository;
import com.kbtg.bootcamp.posttest.repositoty.UsersRepository;
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

    public ResponseEntity<LotteryResponseDto> getAllLotteries(){
        List<Lottery> lotteries = lotteryRepository.findAll();
        List<String> tickets = lotteries.stream()
                .map(Lottery::getLotteryNumber)
                .toList();

        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        return ResponseEntity.ok().body(responseDto);

    }

    public ResponseEntity<LotteryResponseDto> createLottery(LotteryRequestDto requestDto) throws Exception{
        if(Objects.isNull(requestDto)){
            throw new BadRequestException("Request body is null");
        }

            Lottery lottery = new Lottery(requestDto.getTickets(), requestDto.getPrice(), requestDto.getAmount());
            lotteryRepository.save(lottery);

            List<String> tickets = Arrays.asList(lottery.getLotteryNumber());
            LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
            return ResponseEntity.status(201).body(responseDto);
    }

    public ResponseEntity<UserTicketResponseDto> buyLottery(Integer userId, String ticketNumber){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        Lottery lottery = lotteryRepository.findByLotteryNumber(ticketNumber)
                .orElseThrow(() ->new NotFoundException("Ticket not found with ID: " + ticketNumber));

        UserTicket userTicket = new UserTicket(1, user, lottery);
        userTicketRepository.save(userTicket);

        UserTicketResponseDto responseDto = new UserTicketResponseDto(userTicket.getId());
        return ResponseEntity.ok().body(responseDto);

    }

    public ResponseEntity<TicketResponseDto> findLotteryByUserId(Integer userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

       Optional<List<Lottery>> list = userTicketRepository.findDistinctLotteriesByUserId(users.getId());
       List<String> ticket = list.get().stream()
               .map(Lottery::getLotteryNumber)
               .toList();

       int count = list.stream().flatMap(Collection::stream).mapToInt(Lottery::getAmount).sum();
       int cost = list.stream().flatMap(Collection::stream).mapToInt(Lottery::getPrice).sum();

       TicketResponseDto responseDto = new TicketResponseDto(ticket, count, cost);
        return ResponseEntity.ok().body(responseDto);
    }

    public ResponseEntity<LotteryResponseDto> deleteTicketByUserId(Integer userId, String ticketNumber){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        Lottery lottery = lotteryRepository.findByLotteryNumber(ticketNumber)
                .orElseThrow(() ->new NotFoundException("Ticket not found with ID: " + ticketNumber));

        List<String> tickets = Arrays.asList(lottery.getLotteryNumber());
        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        userTicketRepository.deleteUserTicketByUserIdAndTicketId(users.getId(), lottery.getId());
        return ResponseEntity.ok().body(responseDto);
    }
}
