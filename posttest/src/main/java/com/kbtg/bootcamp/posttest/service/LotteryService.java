package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import jdk.jfr.Description;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LotteryService implements ImpLotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {

        this.lotteryRepository = lotteryRepository;
    }

    @Override
    public List<LotteryEntity> getAllLottery() {

        return lotteryRepository.findAll();
    }

    @Override
    public LotteryEntity addLotteryToStore(LotteryEntity lotteryEntity) {

        return lotteryRepository.save(lotteryEntity);
    }

    @Override
    public List<LotteryEntity> getRemainLotteryFromStore() {
        return lotteryRepository.getRemainLotteryFromStore();
    }


    @Override
    public void updateStatusLottery(String ticketId, boolean status) {
        Long ticketIdLong = Long.parseLong(ticketId); // Convert ticketId from String to Long

        Optional<LotteryEntity> optionalLottery = lotteryRepository.findById(ticketIdLong);
        if (optionalLottery.isPresent()) {
            LotteryEntity lottery = optionalLottery.get();
            lottery.setStatus(status); // Set status to true
            lotteryRepository.save(lottery); // Save the updated lottery entity
        } else {
            // Handle if the lottery ticket is not found
        }


    }

    @Override
    public List<LotteryEntity> getLotteryEntity(String ticket) {
        return lotteryRepository.getTicketEntity(ticket);
    }


}
