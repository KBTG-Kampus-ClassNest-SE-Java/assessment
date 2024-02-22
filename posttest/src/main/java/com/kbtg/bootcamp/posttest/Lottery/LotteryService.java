package com.kbtg.bootcamp.posttest.Lottery;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Exception.InternalServiceException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

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
        try {
            return lotteryRepository.findAll().stream().map(lottery -> lottery.getTicket()).toList();
        } catch (Exception e) {
            throw new InternalServiceException(e.getMessage());
        }
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
