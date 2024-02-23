package com.kbtg.bootcamp.posttest.Service;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Exception.ConflictException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Buy existing ticket with non-zero amount should return Success")
    public void TestBuyTicketSuccess() {
        String userId = "1234567890";
        String ticketId = "123456";
        Long ticketAmount = 1L;
        Double ticketPrice = 10.0;

        Lottery lottery = new Lottery(ticketId, ticketAmount, ticketPrice);

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

    @Test
    @DisplayName("Buy non-existing ticket should return not found")
    public void TestBuyNonExistingTicketConflict() {
        String userId = "1234567890";
        String ticketId = "123456";

        Lottery lottery = new Lottery();
        lottery.setTicket(ticketId);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTicketService.BuyTicket(userId, ticketId));

        verify(lotteryRepository).findByTicket(ticketId);
        verify(userticketRepository, never()).findByUserIdAndTicket(userId, ticketId);
        verify(lotteryRepository, never()).save(lottery);
        verify(userticketRepository, never()).save(any(UserTicket.class));
    }

    @Test
    @DisplayName("Buy existing ticket with zero amount should return not found")
    public void TestBuyTicketZeroAmountConflict() {
        String userId = "1234567890";
        String ticketId = "123456";
        Long ticketAmount = 0L;
        Double ticketPrice = 10.0;

        Lottery lottery = new Lottery(ticketId, ticketAmount, ticketPrice);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));

        assertThrows(NotFoundException.class, () -> userTicketService.BuyTicket(userId, ticketId));

        verify(lotteryRepository).findByTicket(ticketId);
        verify(userticketRepository, never()).findByUserIdAndTicket(userId, ticketId);
        verify(lotteryRepository, never()).save(lottery);
        verify(userticketRepository, never()).save(any(UserTicket.class));
    }

    @Test
    @DisplayName("Buy existing ticket with already owned ticket should return conflict")
    public void TestBuyTicketAlreadyOwnedConflict() {
        String userId = "1234567890";
        String ticketId = "123456";
        Long ticketAmount = 1L;
        Double ticketPrice = 10.0;

        Lottery lottery = new Lottery(ticketId, ticketAmount, ticketPrice);
        UserTicket existingUserTicket = new UserTicket(userId, "buy", 1L, lottery);
        existingUserTicket.setId(1L);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));
        when(userticketRepository.findByUserIdAndTicket(userId, ticketId)).thenReturn(Optional.of(existingUserTicket));

        assertThrows(ConflictException.class, () -> userTicketService.BuyTicket(userId, ticketId));

        verify(lotteryRepository).findByTicket(ticketId);
        verify(userticketRepository).findByUserIdAndTicket(userId, ticketId);
        verify(lotteryRepository, never()).save(lottery);
        verify(userticketRepository, never()).save(any(UserTicket.class));
    }

    @Test
    @DisplayName("Get existed UserTicket should return success")
    public void TestGetUserTicketSuccess() {
        String userId = "1234567890";
        Lottery lottery1 = new Lottery("123456", 1L, 10.0);
        Lottery lottery2 = new Lottery("123457", 2L, 20.0);
        List<Lottery> lotteries = Arrays.asList(lottery1, lottery2);

        when(lotteryRepository.findByUserTicketUserId(userId)).thenReturn(lotteries);

        UserTicketResponseDto result = userTicketService.getUserTicket(userId);

        assertNotNull(result);
        assertEquals(new UserTicketResponseDto(Arrays.asList("123456", "123457"), 2L, 30.0), result);

        verify(lotteryRepository).findByUserTicketUserId(userId);
    }

    @Test
    @DisplayName("Get non-existing UserTicket should return not found")
    public void TestGetUserTicketNotFound() {
        String userId = "1234567890";

        when(lotteryRepository.findByUserTicketUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> userTicketService.getUserTicket(userId));

        verify(lotteryRepository).findByUserTicketUserId(userId);
    }

    @Test
    @DisplayName("Delete existed UserTicket should return success")
    public void TestDeleteUserTicketSuccess() {
        String userId = "1234567890";
        String ticketId = "123456";
        Long ticketAmount = 1L;
        Double ticketPrice = 10.0;

        when(userticketRepository.findByUserIdAndTicket(userId, ticketId)).thenReturn(Optional.of(new UserTicket(userId, "buy", 1L, new Lottery(ticketId, ticketAmount, ticketPrice))));

        String result = userTicketService.deleteLottery(userId, ticketId);

        assertEquals(ticketId, result);

        verify(userticketRepository).findByUserIdAndTicket(userId, ticketId);
        verify(userticketRepository).delete(any(UserTicket.class));
    }

    @Test
    @DisplayName("Delete existed UserTicket should return success even zero amount of stock of ticket")
    public void TestDeleteUserTicketZeroSuccess() {
        String userId = "1234567890";
        String ticketId = "123456";
        Long ticketAmount = 0L;
        Double ticketPrice = 10.0;

        when(userticketRepository.findByUserIdAndTicket(userId, ticketId)).thenReturn(Optional.of(new UserTicket(userId, "buy", 1L, new Lottery(ticketId, ticketAmount, ticketPrice))));

        String result = userTicketService.deleteLottery(userId, ticketId);

        assertEquals(ticketId, result);

        verify(userticketRepository).findByUserIdAndTicket(userId, ticketId);
        verify(userticketRepository).delete(any(UserTicket.class));
    }

    @Test
    @DisplayName("Delete non-existing UserTicket should return not found")
    public void TestDeleteUserTicketNotFound() {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userticketRepository.findByUserIdAndTicket(userId, ticketId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTicketService.deleteLottery(userId, ticketId));

        verify(userticketRepository).findByUserIdAndTicket(userId, ticketId);
        verify(userticketRepository, never()).delete(any(UserTicket.class));
    }
}