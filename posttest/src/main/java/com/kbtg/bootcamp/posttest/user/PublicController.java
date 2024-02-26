package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.response.UserTicketDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/users/{userId}/lotteries")
    public UserTicketDetailResponse getUserTicket(@PathVariable("userId") String userId) {
        return this.publicService.getUserTicket(userId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody UserRegisterDto requestDto) {
        return this.publicService.registerUser(requestDto);
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer>  buyLottery(@PathVariable(name = "userId") String userId,
                                            @PathVariable(name = "ticketId") Integer ticketId) {
        return this.publicService.buyLottery(userId, ticketId);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> deleteUserTicket(@PathVariable(name="userId") String userId,
                                       @PathVariable(name="ticketId") Integer ticketId){
        return this.publicService.deleteUserTicket(userId, ticketId);
    }
}
