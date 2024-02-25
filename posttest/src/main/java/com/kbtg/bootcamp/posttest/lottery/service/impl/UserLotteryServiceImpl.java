package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.User;
import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserLotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserRepository;
import com.kbtg.bootcamp.posttest.lottery.service.UserLotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLotteryServiceImpl  implements UserLotteryService {

    private final UserLotteryRepository userLotteryRepository;

    private final UserRepository userRepository;

    private final LotteryRepository lotteryRepository;

    @Override
    public UserLottery addUserLottery(UserLottery userLottery) {
        return null;
    }

    @Override
    public ResponseEntity<Object> buyLottery(String userId, String ticketId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Lottery> lotteryOptional = lotteryRepository.findByTicket(ticketId);
        if (lotteryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserLottery userLottery = new UserLottery();
        userLottery.setUser(userOptional.get());
        userLottery.setLottery(lotteryOptional.get());
        userLottery.setAmount(1);
        userLottery.setCost(lotteryOptional.get().getPrice());
        UserLottery savedUserTicket = userLotteryRepository.save(userLottery);
        return new ResponseEntity<>((savedUserTicket), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserLottery>> getUserLotteryTickets(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserLottery> userTickets = userLotteryRepository.findByUser(userOptional.get());
        return ResponseEntity.ok(userTickets);
    }


    @Override
    public ResponseEntity<UserLottery> sellLotteryTicket(String userId, String ticketId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<UserLottery> userLotteryOptional = userLotteryRepository.findById(Long.parseLong(ticketId));
        if (userLotteryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserLottery userLottery = userLotteryOptional.get();
        if (!userLottery.getUser().getUserId().equals(userId)) {
            return ResponseEntity.badRequest().body(null);
        }
        userLotteryRepository.delete(userLottery);
        return ResponseEntity.ok(userLottery);
    }
}
