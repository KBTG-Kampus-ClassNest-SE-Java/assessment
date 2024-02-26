package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.exception.LotteryException;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryListResDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryServiceImp implements LotteryService {

    private final LotteryRepo lotteryRepo;

    @Autowired
    public LotteryServiceImp(LotteryRepo lotteryRepo) {
        this.lotteryRepo = lotteryRepo;
    }


    //EXP02
    @Override
    public LotteryListResDto listAllLotteries() {
        //instantiate and return response
        return new LotteryListResDto(lotteryRepo.findAll()
                .stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList()));
    }

    //EXP01
    @Override
    public LotteryResponseDto createLottery(LotteryRequestDto lotteryRequestDto) {
        Optional<Lottery> ticket = lotteryRepo.findById(lotteryRequestDto.getTicket());

        //check if the ticketId already exists in the database
        if (ticket.isPresent()) {
            throw new LotteryException("This lottery number has already existed in database.");
        }
        //save newly instantiated lottery object to database, and return ticketId
        return new LotteryResponseDto(lotteryRepo.save(new Lottery(lotteryRequestDto.getTicket(),
                        lotteryRequestDto.getAmount(),
                        lotteryRequestDto.getPrice()))
                .getTicket()
        );
    }
}
