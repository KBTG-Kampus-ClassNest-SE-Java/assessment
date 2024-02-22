
package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exeption.BadRequestException;
import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryService {

    private final List<Lottery> lotteries = new ArrayList<>(List.of(
            new Lottery("111111", 1, 80)
    ));

    private Lottery lottery;

    public List<String> getAllLotteryTickets() {
        List<String> tickets = new ArrayList<>();
        for (Lottery lottery : lotteries) {
            tickets.add(String.valueOf(lottery.getId()));
        }
        return tickets;
    }


    public LotteryResponse addLottery(LotteryRequest request) throws Exception {
        Lottery newLottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());
        lotteries.add(lottery);
        if (newLottery.getId() == null) {
            throw new BadRequestException("Invalid Ticket ID");
        } else {
            return new LotteryResponse(newLottery.getId());
        }

    }

    public Lottery getLotteryById(String lotteryId) {
        return lotteries.stream().filter(lottery -> lottery.getId().equals(lotteryId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lottery ticket no. " + lotteryId + " sold out"));
    }

}
