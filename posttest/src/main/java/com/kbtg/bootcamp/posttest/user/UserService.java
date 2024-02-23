package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userLottery.UserLotteryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final LotteryService lotteryService;

    @Autowired
    private LotteryRepository lotteryRepository;
    private UserRepository userRepository;


    public UserService(LotteryService lotteryService, UserRepository userRepository) {
        this.lotteryService = lotteryService;
        this.userRepository = userRepository;

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }

    public User createUser(UserRequest request) {
        User user = new User(request.name());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserResponse buyLottery(String userId, String lotteryId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        Lottery lottery = lotteryRepository.findById(Long.valueOf(lotteryId))
                .orElseThrow(() -> new NotFoundException("Lottery not found with ID: " + lotteryId));

        user.getLotteries().add(lottery);
        lottery.setUser(user);

        userRepository.save(user);
        lotteryRepository.save(lottery);

        return new UserResponse(lottery.getUser().getId().toString());
    }

    public LotteryResponse deleteLottery(String userId, String ticketId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        List<Lottery> userLotteries = user.getLotteries();
        List<Lottery> soldTickets = new ArrayList<>();

        Lottery lotteryToDelete = user.getLotteries().stream()
                .filter(lottery -> lottery.getId().equals(Long.valueOf(ticketId)))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lottery ticket no." + ticketId + " not found for user"));
        user.getLotteries().remove(lotteryToDelete);
        userRepository.save(user);
        lotteryRepository.delete(lotteryToDelete);

        return new LotteryResponse(String.valueOf(lotteryToDelete.getId()));
    }

    public UserLotteryResponse showUserLotteriesList(String userId) {

        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        List<Lottery> userLotteries = user.getLotteries();

        List<String> lotteryIds = userLotteries.stream()
                .map(lottery -> String.valueOf(lottery.getId()))
                .collect(Collectors.toList());
        List<Integer> prices = userLotteries.stream()
                .map(Lottery::getPrice)
                .toList();

        int count = lotteryIds.size();
        int cost = prices.stream().mapToInt(Integer::intValue).sum();
        return new UserLotteryResponse(lotteryIds, count, cost);
    }
}

