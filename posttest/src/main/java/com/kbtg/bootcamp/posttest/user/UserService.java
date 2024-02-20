package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final LotteryService lotteryService;
    private List<User> users = new ArrayList<>();

    @Autowired
    public UserService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
        // Initialize users list as needed
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    ;

    public User createUser(UserRequest request) {
        User user = new User(request.name());
        users.add(user);
        return user;
    }


    public void buyLottery(String userId, String lotteryId) {
        // Get the user by ID
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Get the lottery by ID
        Lottery lottery = lotteryService.getLotteryById(lotteryId);
        if (lottery == null) {
            throw new IllegalArgumentException("Lottery not found with ID: " + lotteryId);
        }

        // Add the lottery to the user's list of lotteries
        user.getLotteries().add(lottery);
    }

    public List<String> getUserLotteryTickets(String userId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        List<String> tickets = new ArrayList<>();
        List<Lottery> userLotteries =  user.getLotteries();
        for (Lottery lottery : userLotteries) {
            tickets.add(lottery.getId());
        }
        return tickets;
    }

    public void deleteLotteries(String userId){
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        user.getLotteries().clear();
    }
}
