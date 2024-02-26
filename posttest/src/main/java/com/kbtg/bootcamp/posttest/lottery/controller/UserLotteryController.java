package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.annotaion.TenDigitId;
import com.kbtg.bootcamp.posttest.lottery.request.SellLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.service.UserTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/lotteries")
public class UserLotteryController {


    private final UserTicketService userTicketService;

    @PostMapping("/{ticketId}")
    public ResponseEntity<Object> buyLottery(@PathVariable @TenDigitId @Valid String userId, @PathVariable String ticketId) {
        return userTicketService.buyLottery(userId, ticketId);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserPurchasedLotteries(@PathVariable @TenDigitId @Valid String userId) {
        return userTicketService.getUserLotteryTickets(userId);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<SellLotteryResponse> sellLottery(@PathVariable @TenDigitId @Valid String userId, @PathVariable String ticketId) {
        return userTicketService.sellLotteryTicket(userId, ticketId);
    }
}
