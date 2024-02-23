package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/lotteries")
    public LotteryListResponseDto getLotteryList() {
        try {
            LotteryListResponseDto response = userService.getAllLotteries();
            return new ResponseEntity<>(response, HttpStatus.OK).getBody();
        } catch (Exception exception) {
            throw new InternalServiceException("Internal service exception with Normal service");
        }
    }

//    @PostMapping("/users/{userId}/lotteries/{ticketId}")
//    public ResponseEntity<UserTicketsRequestDto> buyLotteries(
//            @PathVariable("userId")
//            Long userId,
//            @PathVariable("ticketId") @NotBlank @Size(min = 6, max = 6)
//            String ticketId) {
//
//        this.userService.buyLotteryTicket(userId, ticketId);
//        return null;
//    }


    }
