package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.GetLotteriesResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryStoreService lotteryStoreService;

    public LotteryController(LotteryStoreService lotteryStoreService) {
        this.lotteryStoreService = lotteryStoreService;
    }

    @GetMapping
    public ResponseEntity<GetLotteriesResponse> getLotteries() {
        List<Lottery> lotteries = this.lotteryStoreService.getAvailableLotteries();

        List<String> tickets = new ArrayList<>();
        for (Lottery l : lotteries) {
             for (int i = 0; i < l.getAmount(); i++) {
                 tickets.add(l.getTicket());
             }
        }

        return ResponseEntity.ok(new GetLotteriesResponse(tickets));
    }
}
