
package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exeption.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LotteryService {
    @Autowired
    private LotteryRepository lotteryRepository;

    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll();
    }

    public void deleteAllLotteries() {
        lotteryRepository.deleteAll();
    }


    public LotteryResponse addLottery(LotteryRequest request) throws Exception {
        Lottery newLottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());
        if (newLottery.getId() == null) {
            throw new BadRequestException("Invalid Ticket ID");
        } else {
            lotteryRepository.save(newLottery);
            return new LotteryResponse(Collections.singletonList(String.valueOf(newLottery.getId())));
        }
    }

    public LotteryResponse getLotteryById(String lotteryId) {
        return null;
    }

}
