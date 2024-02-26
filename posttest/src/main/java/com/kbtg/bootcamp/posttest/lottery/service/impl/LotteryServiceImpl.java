package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.Respone.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.request.AdminLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepository lotteryRepository;

    @Override
    public ResponseEntity<Object> addLottery(AdminLotteryRequest lotteryRequest) {
        if (lotteryRepository.findByTicket(lotteryRequest.ticket()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        Lottery savedLottery = lotteryRepository.save(lotteryRequest.toLottery());
        return new ResponseEntity<>((savedLottery), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LotteryResponse> getAllLotteries() {
        List<Lottery> lotteries = lotteryRepository.findAll();
        List<String> tickets = lotteries.stream().map(Lottery::getTicket).collect(Collectors.toList());
        LotteryResponse lotteryResponse = new LotteryResponse(tickets);
        return ResponseEntity.ok(lotteryResponse);
    }

}
