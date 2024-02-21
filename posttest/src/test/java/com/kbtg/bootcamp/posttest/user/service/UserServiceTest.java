package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.exception.ResourceUnavailableException;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LotteryTicketRepository lotteryTicketRepository;

    @MockBean
    private UserTicketService userTicketService;

    @Test
    public void testPurchaseLotteryTicketSuccess() {
        User user = new User();
        user.setId(1);
        user.setUserId("1234567890");
        when(userRepository.findByUserId(user.getUserId())).thenReturn(user);

        LotteryTicket lotteryTicket = new LotteryTicket();
        lotteryTicket.setId(1);
        lotteryTicket.setTicket("123456");
        lotteryTicket.setPrice(80);
        lotteryTicket.setAmount(1);
        when(lotteryTicketRepository.findByTicket(lotteryTicket.getTicket())).thenReturn(lotteryTicket);

        UserTicketResponse userTicketResponse = new UserTicketResponse(1);
        when(userTicketService.createUserTicketTransaction(user, lotteryTicket)).thenReturn(userTicketResponse);

        UserTicketResponse actualResult = userService.purchaseLotteryTicket(user.getUserId(), lotteryTicket.getTicket());

        assertEquals(userTicketResponse.id(), actualResult.id());
    }

    @Test
    public void testPurchaseLotteryTicketWithUserNotFound() {
        when(userRepository.findByUserId("9999999999")).thenReturn(null);

        assertThrows(ResourceUnavailableException.class, () -> {
            userService.purchaseLotteryTicket("9999999999", "123456");
        });
    }

    @Test
    public void testPurchaseLotteryTicketWithNotFoundOrOutOfStockTicket() {
        when(userRepository.findByUserId("1234567890")).thenReturn(new User());
        when(lotteryTicketRepository.findByTicket("999999")).thenReturn(null);

        assertThrows(ResourceUnavailableException.class, () -> {
            userService.purchaseLotteryTicket("1234567890", "999999");
        });

        LotteryTicket outOfStockTicket = new LotteryTicket();
        outOfStockTicket.setAmount(0);
        when(lotteryTicketRepository.findByTicket("outOfStockTicketId")).thenReturn(outOfStockTicket);

        assertThrows(ResourceUnavailableException.class, () -> {
            userService.purchaseLotteryTicket("1234567890", "888888");
        });
    }

}