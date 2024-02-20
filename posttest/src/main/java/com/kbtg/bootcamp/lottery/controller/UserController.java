package com.kbtg.bootcamp.lottery.controller;

import com.kbtg.bootcamp.lottery.exception.BadRequestException;
import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryUserResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.util.regex.Pattern.matches;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LotteryService lotteryService;

    @Autowired
    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }


    @GetMapping("/{userId}/lotteries")
    public LotteryUserResponseDto getLotteries(@PathVariable("userId") String userId) throws Exception {
        String userValidationResult = validateUserId(userId);
        if (!userValidationResult.equals("valid")) {
            throw new BadRequestException(userValidationResult);
        }

        return lotteryService.getUserLotteryTickets(userId);
    }

    @PostMapping("/{userId}/lotteries/{lotteryNumber}")
    public LotteryPurchaseResponseDto purchaseLotteryTicket(@PathVariable("userId") String userId, @PathVariable("lotteryNumber") String lotteryNumber) throws Exception {
        String userValidationResult = validateUserId(userId);
        if (!userValidationResult.equals("valid")) {
            throw new BadRequestException(userValidationResult);
        }

        String lotteryValidationResult = validateLotteryNumber(lotteryNumber);
        if (!lotteryValidationResult.equals("valid")) {
            throw new BadRequestException(lotteryValidationResult);
        }

        return lotteryService.purchaseLotteryTicket(userId, lotteryNumber);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponseDto deleteLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId) throws Exception {

        String userValidationResult = validateUserId(userId);
        if (!userValidationResult.equals("valid")) {
            throw new BadRequestException(userValidationResult);
        }

        String ticketValidationResult = validateTicketId(ticketId);
        if (!ticketValidationResult.equals("valid")) {
            throw new BadRequestException(ticketValidationResult);
        }

        return lotteryService.soldBackLotteryTicket(userId, ticketId);
    }

    private String validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            return "User ID cannot be null or empty.";
        }
        if (userId.isEmpty() || userId.length() > 100) {
            return "User ID must be between 1 and 100 characters.";
        }
        if (!matches("^[a-zA-Z0-9]*$", userId)) {
            return "User ID must be alphanumeric.";
        }
        return "valid";
    }

    private String validateLotteryNumber(String lotteryNumber) {
        if (lotteryNumber == null || lotteryNumber.isBlank()) {
            return "Lottery number cannot be null or empty.";
        }
        if (lotteryNumber.length() != 6) {
            return "Lottery number must be 6 numbers.";
        }
        if (!matches("^[0-9]*$", lotteryNumber)) {
            return "Lottery number must be numeric.";
        }
        return "valid";
    }

    private String validateTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return "TicketId cannot be null or empty.";
        }
        if (!matches("^[0-9]*$", ticketId)) {
            return "TicketId must be numeric.";
        }
        return "valid";
    }

}