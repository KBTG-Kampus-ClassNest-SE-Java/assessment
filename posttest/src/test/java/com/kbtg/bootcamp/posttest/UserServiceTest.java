package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.user.UserService;
import com.kbtg.bootcamp.posttest.user.UserTicket;
import com.kbtg.bootcamp.posttest.user.UserTicketRepository;
import com.kbtg.bootcamp.posttest.user.dto.UserTicketResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private UserService userService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test buying lottery ticket for a user should save lottery purchase and user ticket association")
    public void testBuyLotteryTicket() {
        String userId = "1234567890";
        String lotteries = "123456";

        Lottery lottery = new Lottery();
        lottery.setAmount(1);
        lottery.setPrice(80);
        when(lotteryRepository.findByTicket(lotteries)).thenReturn(Optional.of(lottery));

        userService.buyLotteryTicket(userId, lotteries);

        verify(lotteryRepository, times(1)).save(lottery);
        verify(userTicketRepository, times(1)).save(any(UserTicket.class));
    }

    @Test
    @DisplayName("Test getting all lottery tickets for a user should return correct ticket count, total price, and ticket numbers")
    public void testGetAllUserTicket() {
        // Arrange
        String userId = "1111111111";
        List<UserTicket> mockUserTickets = new ArrayList<>();
        mockUserTickets.add(new UserTicket(11,"1111111111", null, "345678", 1, 80));
        mockUserTickets.add(new UserTicket(12,"1111111111", null, "345679", 1, 80));
        when(userTicketRepository.findByUserId(userId)).thenReturn(mockUserTickets);

        // Act
        UserTicketResponseDto responseDto = userService.getUserLottery(userId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(2, responseDto.getCount());
        assertEquals(160, responseDto.getTotalPrice());
        assertTrue(responseDto.getTickets().contains("345678"));
        assertTrue(responseDto.getTickets().contains("345679"));
    }

    @Test
    @DisplayName("Test deleting lottery ticket for a user should return deleted ticket details and update lottery information")
    void testDeleteLottery() {
        String userId = "user123";
        String ticket = "ticket123";
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicket(ticket);
        userTicket.setAmount(1);
        userTicket.setPrice(10);

        when(userTicketRepository.findByUserIdAndTicket(userId, ticket)).thenReturn(userTicket);

        LotteryResponseDto result = userService.deleteLottery(userId, ticket);

        assertNotNull(result);
        assertEquals(ticket, result.getTicket());

        // Verify that userTicketRepository.delete() is called once
        verify(userTicketRepository, times(1)).delete(userTicket);

        // Verify that lotteryRepository.save() is called once with the expected Lottery object
        verify(lotteryRepository, times(1)).save(argThat(lottery ->
                lottery.getTicket().equals(ticket) &&
                        lottery.getAmount() == userTicket.getAmount() &&
                        lottery.getPrice() == userTicket.getPrice()));
    }

}
