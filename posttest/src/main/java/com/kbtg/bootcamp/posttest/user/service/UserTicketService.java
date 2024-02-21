package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.user.model.UserTicket;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTicketService {
    private final UserTicketRepository userTicketRepository;

    @Autowired
    public UserTicketService(UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;
    }

    @Transactional
    public UserTicketResponse createUserTicketTransaction(User user, LotteryTicket lotteryTicket) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUser(user);
        userTicket.setLottery(lotteryTicket);

        UserTicket savedUserTicket = userTicketRepository.save(userTicket);

        return new UserTicketResponse(savedUserTicket.getId());
    }
}
