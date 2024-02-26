package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.User;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ThaiLotteryServiceTest {
    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private ThaiLotteryService thaiLotteryService;

    @Test
    @DisplayName("Test add lottery")
    public void addLottery() {
        var lottery = new Lottery("000011", BigDecimal.valueOf(80), 1);

        when(lotteryRepository.save(lottery)).thenReturn(lottery);

        var lotteryResponse = thaiLotteryService.addLottery(lottery);

        assertNotNull(lotteryResponse);

        verify(lotteryRepository).save(lottery);
    }

    @Test
    @DisplayName("Test list all my lottery")
    public void listLotteries() {
        var lottery1 = "000011";
        var lottery2 = "000012";

        when(lotteryRepository.findDistinctTickets()).thenReturn(List.of(lottery1, lottery2));

        var lotteriesResponse = thaiLotteryService.listLotteries();

        assertNotNull(lotteriesResponse);

        verify(lotteryRepository).findDistinctTickets();
    }

    @Test
    @DisplayName("Test buy lottery")
    public void buyLottery() {
        var userId = 1000000001;
        var user = new User("User 1");
        user.setUserId(userId);

        var ticketId = 1;
        var lottery = new Lottery("000011", BigDecimal.valueOf(80), 1);
        lottery.setCurrentAmount(lottery.getAmount());

        var userTicket = new UserTicket(user, lottery);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.of(lottery));
        when(userTicketRepository.save(userTicket)).thenReturn(userTicket);
        when(lotteryRepository.save(lottery)).thenReturn(lottery);

        var buyLotteryResponse = thaiLotteryService.buyLottery(userId, ticketId);

        assertNotNull(buyLotteryResponse);

        verify(userRepository).findById(userId);
        verify(lotteryRepository).findById(ticketId);
        verify(userTicketRepository).save(userTicket);
        verify(lotteryRepository).save(lottery);
    }

    @Test
    @DisplayName("Test sell back my lottery")
    public void sellBackMyLottery() {
        var userId = 1000000001;
        var user = new User("User 1");
        user.setUserId(userId);

        var ticketId = 1;
        var lottery = new Lottery("000011", BigDecimal.valueOf(80), 1);
        lottery.setId(ticketId);
        lottery.setCurrentAmount(lottery.getAmount());

        var userTicket = new UserTicket(user, lottery);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lotteryRepository.findById(ticketId)).thenReturn(Optional.of(lottery));
        when(userTicketRepository.findFirstByUserUserIdAndLotteryId(userId, ticketId)).thenReturn(userTicket);
        when(lotteryRepository.save(lottery)).thenReturn(lottery);

        var lotteryResponse = thaiLotteryService.sellBackMyLottery(userId, ticketId);

        assertNotNull(lotteryResponse);

        verify(userRepository).findById(userId);
        verify(lotteryRepository).findById(ticketId);
        verify(userTicketRepository).findFirstByUserUserIdAndLotteryId(userId, ticketId);
        verify(userTicketRepository).delete(userTicket);
        verify(lotteryRepository).save(lottery);
    }
}
