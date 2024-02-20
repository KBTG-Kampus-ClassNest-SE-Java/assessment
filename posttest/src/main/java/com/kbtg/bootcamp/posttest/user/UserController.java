package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("")
    public List<Profile> helloProfile() {
        return
        lotteryService.getAllUserProfile();
    }

    @GetMapping("/lotteries")
    public UserResponse getLotteriesPage() {
        List<String> collect = lotteryService.getAllLotteries().stream()
                .map(lottery -> lottery.getTicket())
                .collect(Collectors.toList());
        return new UserResponse(collect);
    }

    @PostMapping("/{requestedUserId}/lotteries/{requestedTicketId}")
    public void getBuyLotteryPage(
        @PathVariable Integer requestedUserId,
        @PathVariable Integer requestedTicketId,
        @RequestBody UserRequest request
    ) {
        lotteryService.buyLotteries(request);
    }
}
