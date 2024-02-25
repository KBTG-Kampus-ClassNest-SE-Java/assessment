package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import com.kbtg.bootcamp.posttest.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LotteryController {

    @Autowired
    private final LotteryService lotteryService;

    @Autowired
    private final UserTicketService userTicketService;

    public LotteryController(LotteryService lotteryService, UserTicketService userTicketService) {
        this.lotteryService = lotteryService;
        this.userTicketService = userTicketService;
    }
    //exp1
    @GetMapping("/lotteries")
    public ResponseEntity<Object> getLotteries(){
        List<String> tickets = lotteryService.getLottery();
        LotteryResponseDto lotteryResponse = new LotteryResponseDto(tickets);
        return new ResponseEntity<>(lotteryResponse, HttpStatus.OK);
    }
    //exp2
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/lotteries")
    public ResponseEntity<Object>  addLotteries(@Validated @RequestBody LotteryRequestDto requestDto) throws Exception{

        return new ResponseEntity<>(Map.of("ticket",lotteryService.addLottery(requestDto))
                ,HttpStatus.OK);
    }

    //exp3
    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Object> buyLotteries(@PathVariable("userId") String id, @PathVariable("ticketId") String ticketId){
        Integer ticket  = userTicketService.buyLottery(id,ticketId);
        return new ResponseEntity<>(Map.of("id",new UserTicketResponseDto(ticket).getId()),HttpStatus.CREATED);
    }

    //exp4
    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<Object> getLotteriesById(@PathVariable("userId") String id){
        List<String> ticketId = new ArrayList<>();
        List<UserTicket> userTickets = userTicketService.getLotteriesById(id);
        for(UserTicket ticket : userTickets){
            ticketId.add(ticket.getTicket_id());
        }
        int count = ticketId.size();
        int cost = 80*count;
        return new ResponseEntity<>(Map.of("ticket",ticketId,
                "count",count,
                "cost",cost),HttpStatus.OK);
    }

    //exp5
    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Object> sellLotteries(@PathVariable("userId") int id, @PathVariable("ticketId") String ticketId){
        userTicketService.sellLotteries(id,ticketId);
        UserTicketResponseDto userTicketResponse = new UserTicketResponseDto(ticketId);
        return new ResponseEntity<>(Map.of("ticket",userTicketResponse.getTicket()),HttpStatus.OK);
    }
}
