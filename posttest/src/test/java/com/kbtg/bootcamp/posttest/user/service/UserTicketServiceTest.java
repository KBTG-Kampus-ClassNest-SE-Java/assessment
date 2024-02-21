package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.user.model.UserTicket;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserTicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class UserTicketServiceTest {

    @Autowired
    private UserTicketService userTicketService;

    @MockBean
    private UserTicketRepository userTicketRepository;
    @Test
    void createUserTicketTransaction() {
        User user = new User();
        user.setId(1);

        LotteryTicket lotteryTicket = new LotteryTicket();
        lotteryTicket.setId(1);

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        userTicket.setUser(user);
        userTicket.setLottery(lotteryTicket);

        when(userTicketRepository.save(any(UserTicket.class))).thenReturn(userTicket);

        UserTicketResponse actualResult = userTicketService.createUserTicketTransaction(user, lotteryTicket);

        assertEquals(userTicket.getId(), actualResult.id());
    }
}
