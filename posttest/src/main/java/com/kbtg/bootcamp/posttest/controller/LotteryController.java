package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> getAllLotteries(){

        return lotteryService.getAllLotteries();
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketResponseDto> buyLottery(@Valid @PathVariable Integer userId, @PathVariable String ticketId){
        return lotteryService.buyLottery(userId, ticketId);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<TicketResponseDto> findLotteryByUserId(@Valid @PathVariable Integer userId){
        return lotteryService.findLotteryByUserId(userId);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<LotteryResponseDto> deleteTicketByUserId(@Valid @PathVariable Integer userId, @PathVariable String ticketId){
        return lotteryService.deleteTicketByUserId(userId, ticketId);
    }

}
