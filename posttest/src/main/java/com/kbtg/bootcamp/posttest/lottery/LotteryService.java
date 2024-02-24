package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.StatusInternalServerErrorException;
import com.kbtg.bootcamp.posttest.payload.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.payload.LotteryResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Transactional
    public LotteryResponseDto createLottery(LotteryRequestDto requestDto) {

        Optional<Lottery> optionalTicket = lotteryRepository.findByTicket(requestDto.ticket());
        if (optionalTicket.isPresent() && optionalTicket.get().getAmount() != 0) {
            Lottery ticket = optionalTicket.get();
            if (ticket.getAmount() != 0) {
                throw new StatusInternalServerErrorException("This Ticket ID is already in the store");
            } else {
                ticket.setAmount(1);
                lotteryRepository.save(ticket);
                return new LotteryResponseDto(ticket.getTicket());
            }
        }
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.ticket());
        lottery.setPrice(requestDto.price());
        lottery.setAmount(requestDto.amount());
        lotteryRepository.save(lottery);
        return new LotteryResponseDto(lottery.getTicket());

    }

    public LotteryListResponseDto getLotteries() {
        try {
            return new LotteryListResponseDto(lotteryRepository.getAllTicket());
        } catch (Exception e) {
            throw new StatusInternalServerErrorException("Internal service exception with Normal service");
        }
    }
}

