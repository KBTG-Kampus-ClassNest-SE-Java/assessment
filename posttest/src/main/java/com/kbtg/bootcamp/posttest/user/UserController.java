package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("lotteries")
    public ResponseEntity<Object> getLottery(){
        List<Lottery> lotteries = lotteryService.getAllLotteries();
        Map<String,List<Lottery>> data = new HashMap<>();
        data.put("tickets", lotteries);
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Object>  buyLottery(@PathVariable("userId") String user_id,
                                             @PathVariable("ticketId") String ticket_id){
        String user_ticket_id  = lotteryService.buyLottery(user_id, ticket_id);
        Map<String,String> data = new HashMap<>();
        data.put("id", user_ticket_id);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

}
