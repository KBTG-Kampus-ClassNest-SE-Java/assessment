package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.repositoty.LotteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public ResponseEntity<LotteryResponseDto> getAllLotteries(){
        List<Lottery> lotteries = lotteryRepository.findAll();
        List<String> tickets = lotteries.stream()
                .map(lottery -> lottery.getLottery_number())
                .collect(Collectors.toList());

        LotteryResponseDto responseDto = new LotteryResponseDto(tickets);
        return ResponseEntity.ok().body(responseDto);

    }

}
