package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import com.kbtg.bootcamp.posttest.service.impl.ImpUserTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserLotteryController {

    private final ImpLotteryService impLotteryService;
    private final ImpUserTicketService impUserTicketService;


    public UserLotteryController(ImpLotteryService impLotteryService, ImpUserTicketService impUserTicketService) {
        this.impLotteryService = impLotteryService;
        this.impUserTicketService = impUserTicketService;
    }

    @GetMapping("/users/lotteries")
    public List<LotteryEntity> getRemainLotteryFromStore() {

        try {
            List<LotteryEntity> lotteryRemain = impLotteryService.getRemainLotteryFromStore();
            if (lotteryRemain == null) {

                return Collections.emptyList();

            } else {

                return lotteryRemain;
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

    @GetMapping("/users/lotteries/{id}")
    public List<UserTicketEntity> getAllOwnLotteryFromUser(@PathVariable("id") String user_Id) {

        try {

            List<UserTicketEntity> ownLottery = impUserTicketService.getAllOwnLotteryFromUser(user_Id);
            if (ownLottery == null) {

                return Collections.emptyList();
            } else {
                return ownLottery;
            }

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    @PostMapping("/users/lotteries")
    public UserTicketEntity buyLotteryFromStore(@RequestBody UserTicketEntity userTicketEntity) {

//        impLotteryService.updateStatusLottery(userTicketEntity.getTicket(), true); // error wait for fix

        UserTicketEntity userTicketEntity1 = impUserTicketService.buyLotteryFromStore(userTicketEntity);

        return userTicketEntity1;
    }


    @DeleteMapping("/users/{userid}/lotteries/{ticket}")
    public void refundLotteryToStore(@PathVariable String userid, @PathVariable String ticket) {


        impUserTicketService.refundLotteryToStore(userid, ticket);
    }
}


//        public String getRemainLotteryAsJson() {
//            List<LotteryEntity> lotteryList = getRemainLottery();
//            Map<String, Object> result = new HashMap<>();
//            if (lotteryList == null) {
//                // If lotteryList is null, create a JSON object with specific key-value pairs
//                result.put("Id", 1);
//                result.put("userid", "mo");
//            } else {
//                // If lotteryList is not null, serialize it to JSON
//                result.put("lotteryList", lotteryList);
//            }
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                return objectMapper.writeValueAsString(result);
//            } catch (JsonProcessingException e) {
//                // Handle the exception if JSON serialization fails
//                e.printStackTrace();
//                return "Error occurred while converting to JSON";
//            }