package com.kbtg.bootcamp.posttest.lottery;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public List<String> getLottery() {
        return lotteryRepository.findAll().stream().map(lottery -> lottery.getTicket()).toList();
    }

    public String createLottery(LotteryRequestDto lottery) {
        Lottery newlottery = new Lottery(lottery.ticket(), lottery.amount(), lottery.price());
        lotteryRepository.save(newlottery);

        return lottery.ticket();
    }

    public String deleteLottery(String ticketId) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(Integer.parseInt(ticketId));

        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            String ticket = lottery.getTicket();
            lotteryRepository.delete(lottery);
            return ticket;
        } else {
            // Handle case where the lottery with the given ticketId is not found
            throw new NoSuchElementException("Lottery with ticketId " + ticketId + " not found");
        }
    }
}
