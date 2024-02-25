package com.kbtg.bootcamp.posttest.lottery.rest.controller;


import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryListResDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class LotteryController {

    private final LotteryServiceImp lotteryServiceImp;
    @Autowired
    public LotteryController(LotteryServiceImp lotteryServiceImp) {
        this.lotteryServiceImp = lotteryServiceImp;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryResponseDto> lotteryAdminPost(@RequestBody LotteryRequestDto request){
        return new ResponseEntity<>(new LotteryResponseDto(lotteryServiceImp.createLottery(request).getTicket()), HttpStatus.CREATED);
    }

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryListResDto> listLotteries(){
        return new ResponseEntity<>(lotteryServiceImp.listAllLotteries(), HttpStatus.OK);
    }
}
