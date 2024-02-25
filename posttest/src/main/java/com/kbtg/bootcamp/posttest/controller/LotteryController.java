package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.request.LotteryRequest;
import com.kbtg.bootcamp.posttest.response.TicketResponse;
import com.kbtg.bootcamp.posttest.response.TicketsResponse;
import com.kbtg.bootcamp.posttest.service.LotteryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;
    LotteryController(LotteryService lotteryService){
        this.lotteryService = lotteryService;

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public TicketsResponse getAllTickets(){

        return lotteryService.getAllTickets();
    }
}
