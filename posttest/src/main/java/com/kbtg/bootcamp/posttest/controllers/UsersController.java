package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.models.lottery.TicketIdListResponseDTO;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;

import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketInfoResponseDTO;
import com.kbtg.bootcamp.posttest.services.UsersSerivce;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.*;



@RestController
public class UsersController {

    private final UsersSerivce usersSerivce;

    public UsersController(UsersSerivce usersSerivce) {
        this.usersSerivce = usersSerivce;
    }


    @GetMapping("/lotteries")
    public TicketIdListResponseDTO getBuyAbleLotteries(){
        return usersSerivce.getBuyAbleLotteries();
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public UserTicketIdResponseDTO buyTicket(
            @PathVariable @Pattern(regexp = "[0-9]{10}", message = "User must be a 10 digits number") String userId,
            @PathVariable @Pattern(regexp = "[0-9]{6}", message = "Ticket must be a 6 digits number") String ticketId
    ){
        return usersSerivce.buyTicket(userId, ticketId);

    }

    @GetMapping("/users/{userId}/lotteries")
    public UserTicketInfoResponseDTO getAllUserLotteriesInfo(
            @PathVariable @Pattern(regexp = "[0-9]{10}", message = "User must be a 10 digits number") String userId
    ){
        return usersSerivce.getAllUserLotteriesInfo(userId);
    }


    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public TicketIdResponseDTO sellUserTicket(
            @PathVariable @Pattern(regexp = "[0-9]{10}", message = "User must be a 10 digits number") String userId,
            @PathVariable @Pattern(regexp = "[0-9]{6}", message = "Ticket must be a 6 digits number") String ticketId
    ){
        return usersSerivce.sellUserTicket(userId, ticketId);
    }
}
