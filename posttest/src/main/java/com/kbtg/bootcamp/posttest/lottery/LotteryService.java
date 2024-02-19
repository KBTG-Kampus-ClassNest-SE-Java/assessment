package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.security.controller.AdminRequest;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LotteryService {
    @Autowired
    private final LotteryRepository lotteryRepository;


    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public Optional<Lottery> getLottery(Long id) {
        return lotteryRepository.findById(id);
    }

    public LotteryResponse createLottery(AdminRequest request) {
        Objects.requireNonNull(request);
        Lottery newLottery = new Lottery(request.ticket(), request.price(), request.amount());

        if (newLottery.getId() != null ) {
            throw new DuplicateTickerException("Duplicate entity");
        } else {
            // new lottery
            lotteryRepository.save(newLottery);
        }

        return new LotteryResponse(newLottery.getTicket());
    }

    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll();
    }
}
