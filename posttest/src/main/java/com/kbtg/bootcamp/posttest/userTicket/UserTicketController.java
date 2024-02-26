package com.kbtg.bootcamp.posttest.userTicket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserTicketController {

    private final UserTicketService userTicketService;

    @Autowired
    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public UserTicketResponse buyLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userTicketService.buyLottery(userId,ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public UserTicketResponse sellBackLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userTicketService.deleteLotteryLotteryById(userId, ticketId);
    }

    @GetMapping("/{userId}/lotteries")
    public UserTicketResponse getUserLotteries(@PathVariable("userId") String userId) {
        return userTicketService.showUserLotteriesList(userId);
    }
}
