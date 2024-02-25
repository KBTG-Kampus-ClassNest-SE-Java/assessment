package com.example.ptts.controller;


import com.example.ptts.entity.LotteryResponseDto;
import com.example.ptts.entity.LotteryUserResponseDto;
import com.example.ptts.entity.UserTicketResponseDto;
import com.example.ptts.service.LotteryService;
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

        return lotteryService.findAllLotteries();
    }
    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketResponseDto> buyLottery(@PathVariable Integer userId, @PathVariable String ticketId){
        return lotteryService.buyLottery(userId, ticketId);
    }


}
