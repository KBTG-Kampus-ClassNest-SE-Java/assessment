
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryService {

    private List<Lottery> lotteries = new ArrayList<>();

//    @Getter
//    @Value("${lottery.price}")
//    private Integer lotteryPrice;

    private Lottery lottery;
    public List<String> getAllLotteryTickets() {
        List<String> tickets = new ArrayList<>();
        for (Lottery lottery : lotteries) {
            tickets.add(lottery.getId());
        }
        return tickets;
    }

    public String addLottery(LotteryRequest request) {
        Lottery lottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());
        lotteries.add(lottery);
        return lottery.getId();
    }

    public Lottery getLotteryById(String lotteryId) {
        for (Lottery lottery : lotteries) {
            if (lottery.getId().equals(lotteryId)) {
                return lottery;
            }
        }
        return null;
    }



    public List<Lottery> getUserLotteries(String userId) {
        List<Lottery> userLotteries = new ArrayList<>();
        for (Lottery lottery : lotteries) {
            if (lottery.getId().equals(userId)) {
                userLotteries.add(lottery);
            }
        }
        return userLotteries;
    }


    public Integer getLotteryPrice() {
        return lottery.getPrice();
    }

}
