package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.ProductResponseDTO;
import com.kbtg.bootcamp.posttest.dto.UserResponseDto;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserResponseDto> buyLotteryTicket(@PathVariable String userId, @PathVariable String ticketId)throws Exception {
        UserResponseDto responseDto;
        responseDto = this.userService.buyLotteryTicketProcess(userId,ticketId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<ProductResponseDTO> getAllMyTickets(@PathVariable String userId)throws Exception {
        ProductResponseDTO responseDto;
        responseDto = this.userService.getAllMyTicketsProcess(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<ProductResponseDTO> deleteLotteryTicket(@PathVariable String userId, @PathVariable String ticketId)throws Exception {
        ProductResponseDTO responseDto;
        responseDto = this.userService.deleteLotteryTicketProcess(userId,ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
