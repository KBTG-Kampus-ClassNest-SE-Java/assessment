package com.kbtg.bootcamp.posttest.userTicket;

import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserTicketServiceTest {

    MockMvc mockMvc;

    @Mock
    private UserTicketRepository userTicketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LotteryRepository lotteryRepository;


    @BeforeEach
    void setUp() {
        UserTicketService userTicketService = new UserTicketService(userTicketRepository, lotteryRepository, userRepository);
    }
}