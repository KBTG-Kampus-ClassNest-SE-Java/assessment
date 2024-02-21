package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final LotteryService lotteryService;
    private List<User> users = new ArrayList<>(List.of(
            new User("SirA")
    ));

    @Autowired
    public UserService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
        // Initialize users list as needed
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String userId) {
        return users.stream().filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User : " + userId + " doesn't exist"));
    }

    public User createUser(UserRequest request) {
        User user = new User(request.name());
        users.add(user);
        return user;
    }


    public void buyLottery(String userId, String lotteryId) {
        User user = getUserById(userId);
        Lottery lottery = lotteryService.getLotteryById(lotteryId);
        user.getLotteries().add(lottery);
    }

    public List<String> getUserLotteryTickets(String userId) {
        User user = getUserById(userId);
        List<String> tickets = new ArrayList<>();
        List<Lottery> userLotteries = user.getLotteries();
        for (Lottery lottery : userLotteries) {
            tickets.add(lottery.getId());
        }
        return tickets;
    }

    public List<Lottery> deleteLottery(String userId, String ticketId) {
        User user = getUserById(userId);

        List<Lottery> userLotteries = user.getLotteries();
        List<Lottery> soldTickets = new ArrayList<>();

         soldTickets.add(userLotteries.stream().filter(lotteries -> lotteries.getId().equals(ticketId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lottery ticket no." + ticketId + " not exist")));
        return soldTickets;
    }
}

