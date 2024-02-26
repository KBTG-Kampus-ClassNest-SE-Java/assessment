package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.dto.DeleteUserLotteryResponse;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.exceptions.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResponse> buyLottery(
            @PathVariable(value = "userId")
            @Pattern(regexp = "^[0-9]{10}$", message = "User Id must be 10 digits.")
            @NotNull
            String userId,

            @PathVariable(value = "ticketId")
            @Pattern(regexp = "^[0-9]{6}$", message = "Ticket Id must be 6 digits.")
            @NotNull
            String ticketId
    ) {
        UserTicket userTicket = this.lotteryService.buyLottery(userId, ticketId);

        BuyLotteryResponse response = new BuyLotteryResponse(userTicket.getId().toString());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<GetLotteriesByUserIdResponse> getLotteriesByUserId(
            @PathVariable(value = "userId")
            @Pattern(regexp = "^[0-9]{10}$", message = "User Id must be 10 digits.")
            @NotNull
            String userId
    ) {
        GetLotteriesByUserIdResponse response = this.lotteryService.getLotteriesByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<DeleteUserLotteryResponse> deleteLottery(
            @PathVariable(value = "userId")
            @Pattern(regexp = "^[0-9]{10}$", message = "User Id must be 10 digits.")
            @NotNull
            String userId,

            @PathVariable(value = "ticketId")
            @Pattern(regexp = "^[0-9]{6}$", message = "Ticket Id must be 6 digits.")
            @NotNull
            String ticketId
    ) {
        String soldTicketId = this.lotteryService.sellLottery(userId, ticketId);
        DeleteUserLotteryResponse response = new DeleteUserLotteryResponse(soldTicketId);
        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(value = {
            LotteryNotFoundException.class,
            LotterySoldOutException.class,
            UserTicketNotFoundException.class
    })
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.notFound().build();
    }

}
