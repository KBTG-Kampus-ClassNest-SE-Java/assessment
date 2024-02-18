package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.service.LoterryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LotteryController {

    private final LoterryService loterryService;

    public LotteryController(LoterryService lotteryRepository) {
        this.loterryService = lotteryRepository;
    }

    // Create
    @PostMapping("/admin/lotteries")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketResponseDto> create(@RequestBody LotteryRequestDto lotteryRequestDto) {
        return new ResponseEntity<>(new TicketResponseDto(loterryService.save(lotteryRequestDto).getTicket()), HttpStatus.OK);
    }

    // Read
    @GetMapping("/lotteries")
    public ResponseEntity<TicketListResponseDto> read() {
        return new ResponseEntity<>(loterryService.findAll(), HttpStatus.OK);
    }
}
