
package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryService {

    private final List<Lottery> lotteries = new ArrayList<>(List.of(
            new Lottery("111111",1,80)
    ));

    private Lottery lottery;
    public List<String> getAllLotteryTickets() {
        List<String> tickets = new ArrayList<>();
        for (Lottery lottery : lotteries) {
            tickets.add(lottery.getId());
        }
        return tickets;
    }


    public LotteryResponse addLottery(LotteryRequest request) {
        Lottery lottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());
        lotteries.add(lottery);
        return new LotteryResponse(lottery.getId());
    }

    public Lottery getLotteryById(String lotteryId) {
        return lotteries.stream().filter(lottery -> lottery.getId().equals(lotteryId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lottery ticket no. " + lotteryId + " sold out"));
    }



    public List<Lottery> getUserLotteries(String userId) {
        List<Lottery> userLotteries = new ArrayList<>();
        boolean userFound = false;
        for (Lottery lottery : lotteries) {
            if (lottery.getId().equals(userId)) {
                userLotteries.add(lottery);
                userFound = true;
            }
        }
        if (!userFound) {
            throw new NotFoundException("User lotteries not found for user ID: " + userId);
        }
        return userLotteries;
    }

    public Integer getLotteryPrice() {
        return lottery.getPrice();
    }

}
