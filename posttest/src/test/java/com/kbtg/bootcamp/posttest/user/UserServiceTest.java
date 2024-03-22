package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.InternalServerStatusErrorException;
import com.kbtg.bootcamp.posttest.lottery.LotteryModel;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.payload.LotteryDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LotteryRepository lotteryRepository;

    @Test
    @DisplayName("Buy existing ticket with non-zero amount should return Success")
    public void TestCreateUserAndLotterySuccess() {
        String userId = "1234567890";
        String ticketId = "123456";
        double ticketPrice = 80;
        int ticketAmount = 1;

        LotteryModel lottery = new LotteryModel(ticketId, ticketPrice, ticketAmount);
        UserModel existingUserTicket = new UserModel(1, userId, lottery);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));
        when(userRepository.save(any(UserModel.class))).thenReturn(existingUserTicket);

        String result = userService.createUserAndLottery(userId, ticketId);

        assertEquals(existingUserTicket.getId().toString(), result);

        verify(lotteryRepository).findByTicket(ticketId);
        verify(userRepository).save(any(UserModel.class));
        verify(lotteryRepository).save(lottery);
    }

    @Test
    @DisplayName("Buy non-existing ticket should return StatusInternalServerError")
    public void TestBuyNonExistingTicketStatusInternalServerErrort() {
        String userId = "1234567890";
        String ticketId = "123456";

        LotteryModel lottery = new LotteryModel();
        lottery.setTicket(ticketId);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.empty());

        assertThrows(InternalServerStatusErrorException.class, () -> userService.createUserAndLottery(userId, ticketId));

        verify(lotteryRepository).findByTicket(ticketId);
        verify(lotteryRepository, never()).save(lottery);
        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    @DisplayName("Buy existing ticket with zero amount should return not found")
    public void TestBuyTicketZeroAmountNotAvailable() {
        String userId = "1234567890";
        String ticketId = "123456";
        double ticketPrice = 80;
        int ticketAmount = 0;

        LotteryModel lottery = new LotteryModel(ticketId, ticketPrice, ticketAmount);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));

        assertThrows(InternalServerStatusErrorException.class, () -> userService.createUserAndLottery(userId, ticketId));

        verify(lotteryRepository).findByTicket(ticketId);
        verify(lotteryRepository, never()).save(lottery);
        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    @DisplayName("Get existed UserDetail should return success")
    public void TestGetUserDetailSuccess() {
        String userId = "1234567890";
        LotteryModel lottery1 = new LotteryModel("123456", 80, 1);
        LotteryModel lottery2 = new LotteryModel("123457", 80, 1);
        UserModel user1 = new UserModel(1,"1234567890",lottery1);
        UserModel user2 = new UserModel(2,"1234567890",lottery2);
        List<UserModel> userdetail = List.of(user1, user2);

        when(userRepository.findByUserId(userId)).thenReturn(userdetail);

        LotteryDetailDto result = userService.getUserDetail(userId);

        assertNotNull(result);
        assertEquals(new LotteryDetailDto(Arrays.asList("123456", "123457"), 2, 160), result);

        verify(userRepository).findByUserId(userId);
    }
    @Test
    @DisplayName("Get non-existing UserTicket should return BadRequest")
    public void TestGetUserTicketBadRequest() {
        String userId = "1234567890";

        when(userRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(BadRequestException.class, () -> userService.getUserDetail(userId));

        verify(userRepository).findByUserId(userId);
    }
    @Test
    @DisplayName("Delete existed User should return success")
    public void testSellLottery() {
        String userId = "1234567890";
        String ticketId = "123456";
        LotteryModel lottery1 = new LotteryModel("123456", 80, 1);

        UserModel user = new UserModel(1,userId, lottery1);
        when(userRepository.findByUserId(userId)).thenReturn(List.of(user));

        String result = userService.sellLotteryByUserIdAndTicketId(userId, ticketId);

        assertEquals(ticketId, result);

        verify(userRepository, times(1)).findByUserId(userId);
        verify(userRepository, times(1)).delete(any(UserModel.class));
    }
    @Test
    @DisplayName("Delete non-existing UserTicket should return BadRequest")
    public void TestSellUserTicketNotBadRequest() {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(BadRequestException.class, () -> userService.sellLotteryByUserIdAndTicketId(userId, ticketId));

        verify(userRepository).findByUserId(userId);
        verify(userRepository, never()).delete(any(UserModel.class));
    }
}
