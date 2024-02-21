package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.exception.DuplicationException;
import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketListResponse;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryTicketResponse> addLotteryTicket(
            @Validated
            @RequestBody LotteryTicketRequest request) {

        try {
            LotteryTicketResponse response = lotteryService.createLotteryTicket(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicationException e) {
            throw e;
       } catch (Exception e) {
            throw new InternalServiceException("An internal error occurred when creating a lottery ticket");
        }
    }

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryTicketListResponse> getAllLotteryTickets() {
        try {
            var response = lotteryService.getLotteryTicketList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServiceException("An internal error occurred when getting lottery ticket list");
        }
    }
}
