package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserTicketController {

    private final UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<?> purchaseLottery(
            @PathVariable
            @NotBlank(message = "User ID is mandatory")
            @Pattern(regexp = "^[0-9]{10}$",message = "User ID must be 10 digit numbers")
            String userId,

            @PathVariable
            @NotBlank(message = "Lottery number is mandatory")
            @Pattern(regexp = "^[0-9]{6}$",message = "Lottery number must be 6 digit numbers")
            String ticketId
    ) {
        PurchaseTicketResponseDTO purchaseTicketResponseDTO = userTicketService.purchaseTicket(userId,ticketId);
        return new ResponseEntity<>(purchaseTicketResponseDTO, HttpStatus.CREATED);
    }
}
