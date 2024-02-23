package com.kbtg.bootcamp.posttest.Service;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Exception.ConflictException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public List<String> getLottery() {
        return lotteryRepository.findAll().stream().filter(lottery -> lottery.getAmount() > 0).map(Lottery::getTicket).toList();
//        return lotteryRepository.findTicketsWithAmountGreaterThanZero();
    }

    public String createLottery(LotteryRequestDto lottery) {
        if (lotteryRepository.findFirstByTicket(lottery.ticket()).isPresent())
            throw new ConflictException("Lottery with ticket " + lottery.ticket() + " already exists");
        Lottery newlottery = new Lottery(lottery.ticket(), lottery.amount(), lottery.price());
        lotteryRepository.save(newlottery);

        return lottery.ticket();
    }

    public String deleteLottery(String ticketId) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(Integer.parseInt(ticketId));

        if (optionalLottery.isEmpty())
            throw new NotFoundException("Lottery with ticketId " + ticketId + " not found");

        Lottery lottery = optionalLottery.get();
        String ticket = lottery.getTicket();
        lotteryRepository.delete(lottery);

        return ticket;
    }
}
