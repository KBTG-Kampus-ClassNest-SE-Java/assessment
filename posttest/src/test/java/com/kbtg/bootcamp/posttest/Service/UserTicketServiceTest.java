package com.kbtg.bootcamp.posttest.Service;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserTicketServiceTest {
    @Mock
    private LotteryRepository lotteryRepository;
    @Mock
    private UserTicketRepository userticketRepository;
    @InjectMocks
    private UserTicketService userTicketService;

    @Test
    @DisplayName("Buy Ticket - Success")
    public void buyTicketSuccess() {
        String userId = "1234567890";
        String ticketId = "123456";

        Lottery lottery = new Lottery();
        lottery.setTicket(ticketId);
        lottery.setAmount(1L);

        UserTicket existingUserTicket = new UserTicket(userId, "buy", 1L, lottery);
        existingUserTicket.setId(1L);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));
        when(userticketRepository.findByUserIdAndTicket(userId, ticketId)).thenReturn(Optional.empty());
        when(userticketRepository.save(any(UserTicket.class))).thenReturn(existingUserTicket);

        String result = userTicketService.BuyTicket(userId, ticketId);

        assertEquals(existingUserTicket.getId().toString(), result);

        verify(lotteryRepository).findByTicket(ticketId);
        verify(userticketRepository).findByUserIdAndTicket(userId, ticketId);
        verify(userticketRepository).save(any(UserTicket.class));
        verify(lotteryRepository).save(lottery);
    }
}