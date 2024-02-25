package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.model.UserTicket;
import com.kbtg.bootcamp.posttest.response.IdResponse;
import com.kbtg.bootcamp.posttest.response.TicketResponse;
import com.kbtg.bootcamp.posttest.response.UserTicketResponse;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final LotteryService lotteryService;

    private final UserService userService;
    UserController(LotteryService lotteryService, UserService userService){
        this.userService = userService;
        this.lotteryService = lotteryService;
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{userID}/lotteries/{ticketNumber}")
    public IdResponse buyTicket( @PathVariable("userID") String userID,@PathVariable ("ticketNumber") String ticketNumber){


        return lotteryService.buyTicket(userID,ticketNumber);
        }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{userID}/lotteries/{ticketNumber}")
    public TicketResponse deleteTicket(@PathVariable("userID") String userID, @PathVariable ("ticketNumber") String ticketNumber){


        return lotteryService.sellTicketBack(userID,ticketNumber);
    }



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userID}/lotteries")
    public UserTicketResponse getAllMyTickets(@PathVariable("userID") String userID){
        return lotteryService.getAllUserTickets(userID);
    }








}
