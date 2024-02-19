package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.GetLotteriesResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public ResponseEntity<GetLotteriesResponse> getLotteries() {
        List<Lottery> lotteries = this.lotteryService.getLotteries();
        List<String> tickets = lotteries
                .stream()
                .map(Lottery::getTicket)
                .toList();

        return ResponseEntity.ok(new GetLotteriesResponse(tickets));
    }
}
