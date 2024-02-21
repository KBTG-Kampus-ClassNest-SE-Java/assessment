package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.exception.ResourceUnavailableException;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketResponse> purchaseLotteryTicket(
            @PathVariable("userId") @NotBlank @Size(min = 10, max = 10) String userId,
            @PathVariable("ticketId") @NotBlank @Size(min = 6, max = 6) String ticketId
    ) {
        try {
            UserTicketResponse response = userService.purchaseLotteryTicket(userId, ticketId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceUnavailableException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceException("An internal error occurred when adding a lottery ticket to user");
        }
    }
}
