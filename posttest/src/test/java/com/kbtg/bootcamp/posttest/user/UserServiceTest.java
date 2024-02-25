package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LotteryRepository lotteryRepository;

//    @Test
//    @DisplayName("Get existed UserTicket should return success")
//    public void TestGetUserDetail() {
//        String userId = "1234567890";
//        User user1 = new User(1,1234567890,);
//        User user2 = new User("123457", 80, 1);
//        Optional<User> lotteries = Optional.of(lottery1, lottery2).asList(lottery1, lottery2);
//
//        when(userRepository.findByUserId(userId)).thenReturn(lotteries);
//
//        UserTicketResponseDto result = userTicketService.getUserTicket(userId);
//
//        assertNotNull(result);
//        assertEquals(new UserTicketResponseDto(Arrays.asList("123456", "123457"), 2L, 30.0), result);
//
//        verify(lotteryRepository).findByUserTicketUserId(userId);
//    }
//
//    @Test
//    @DisplayName("Get non-existing UserTicket should return not found")
//    public void TestGetUserTicketNotFound() {
//        String userId = "1234567890";
//
//        when(lotteryRepository.findByUserTicketUserId(userId)).thenReturn(Collections.emptyList());
//
//        assertThrows(NotFoundException.class, () -> userTicketService.getUserTicket(userId));
//
//        verify(lotteryRepository).findByUserTicketUserId(userId);
//    }



}