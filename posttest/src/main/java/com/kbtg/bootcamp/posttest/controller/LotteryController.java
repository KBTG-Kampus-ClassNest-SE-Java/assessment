package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.model.Users;
import com.kbtg.bootcamp.posttest.repositoty.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> getAllLotteries(){

        return lotteryService.getAllLotteries();
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@RequestBody LotteryRequestDto requestDto){
        return lotteryService.createLottery(requestDto);
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketResponseDto> buyLottery(@PathVariable Integer userId, @PathVariable String ticketId){
        return lotteryService.buyLottery(userId, ticketId);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<TicketResponseDto> findLotteryByUserId(@PathVariable Integer userId){
        return lotteryService.findLotteryByUserId(userId);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<LotteryResponseDto> deleteTicketByUserId(@PathVariable Integer userId, @PathVariable String ticketId){
        return lotteryService.deleteTicketByUserId(userId, ticketId);
    }

}
