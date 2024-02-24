package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.userTicket.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.userTicket.UserTicket;
import com.kbtg.bootcamp.posttest.userTicket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userTicket.UserTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final LotteryRepository lotteryRepository;
    private final UserRepository userRepository;
    private final UserTicketRepository userTicketRepository;

    @Autowired
    public UserService(LotteryRepository lotteryRepository, UserRepository userRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userRepository = userRepository;
        this.userTicketRepository = userTicketRepository;
    }




    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }

    public User createUser(UserRequest request) {
        String name = request.getName();
        User user = new User(name);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserTicketResponse buyLottery(String userId, String lottery_id) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        Lottery lottery = lotteryRepository.findById(Long.valueOf(lottery_id))
                .orElseThrow(() -> new NotFoundException("Lottery not found with ID: " + lottery_id));

        userRepository.save(user);
        lotteryRepository.save(lottery);
        UserTicket userTicket = new UserTicket(user.getUser_id(), lottery.getLottery_id());
        userTicketRepository.save(userTicket);
        return new UserTicketResponse(userId);

    }

    public LotteryResponse deleteLottery(String userId, String ticketId) {
        Lottery deletedLottery = lotteryRepository.findById(Long.valueOf(ticketId))
                .orElseThrow(() -> new NotFoundException("Lottery ticket not found"));

        // Delete the corresponding user ticket entry
        UserTicket userTicket = userTicketRepository.findByUserIdAndLotteryId(Long.valueOf(userId), Long.valueOf(ticketId));
        if (userTicket != null) {
            userTicketRepository.delete(userTicket);
        }

        // Create a response containing the ticket numbers of the deleted lotteries
        List<String> deletedTicketNumbers = List.of(deletedLottery.getIdAsString());
        return new LotteryResponse(deletedTicketNumbers);
    }

    public UserLotteryResponse showUserLotteriesList(String userId) {
        Long userIdLong = Long.parseLong(userId);

        List<UserTicket> userTickets = userTicketRepository.findByUserId(userIdLong);

        List<Long> lotteryIds = userTickets.stream()
                .map(UserTicket::getLotteryId)
                .collect(Collectors.toList());

        List<Lottery> lotteries = lotteryRepository.findAllById(lotteryIds);

        List<String> ticketIds = lotteries.stream()
                .map(Lottery::getIdAsString)
                .collect(Collectors.toList());

        return new UserLotteryResponse(ticketIds);
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
    return users;
    }
}

