package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.User;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.repositories.UserRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuyerUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private BuyerUserService buyerUserService;

    @Test
    @DisplayName("Test list all my lottery")
    public void listAllMyLottery() {
        var userId = 1000000001;
        var user = new User("User 1");
        user.setUserId(userId);

        var ticket = "000000";
        var lottery = new Lottery(ticket, BigDecimal.valueOf(80), 1);
        var userTicket = new UserTicket(user, lottery);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userTicketRepository.findByUserUserId(userId)).thenReturn(List.of(userTicket));

        var myLotteryResponse = buyerUserService.listAllMyLottery(userId);

        assertNotNull(myLotteryResponse);

        verify(userRepository).findById(userId);
        verify(userTicketRepository).findByUserUserId(userId);
    }

    @Test
    @DisplayName("Test list all my lottery user not found")
    public void listAllMyLotteryUserNotFound() {
        var userId = 1000000001;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> buyerUserService.listAllMyLottery(userId));

        verify(userRepository).findById(userId);
    }
}
