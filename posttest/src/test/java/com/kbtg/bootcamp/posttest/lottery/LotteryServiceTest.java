package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.InternalServerStatusErrorException;
import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
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

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceTest {

    @InjectMocks
    private LotteryService lotteryService;
    @Mock
    private LotteryRepository lotteryRepository;

    @Test
    @DisplayName("Create lottery should success and return ticket Id as string")
    public void TestCreateLotterySuccess() {
        String ticket = "123456";
        double price = 80;
        int amount = 1;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, price, amount);
        when(lotteryRepository.findByTicket(ticket)).thenReturn(empty());

        String result = lotteryService.createLottery(lotteryRequestDto);

        assertEquals(ticket, result);

        verify(lotteryRepository).findByTicket(ticket);
        verify(lotteryRepository).save(any(LotteryModel.class));
    }

    @Test
    @DisplayName("Create lottery that already exists should throw StatusInternalServerErrorException")
    public void TestCreateDuplicateTicket() {
        String ticket = "123456";
        double price = 80;
        int amount = 1;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, price, amount);

        when(lotteryRepository.findByTicket(ticket)).thenReturn(Optional.of(new LotteryModel(ticket, price, amount)));

        assertThrows(InternalServerStatusErrorException.class, () -> lotteryService.createLottery(lotteryRequestDto));

        verify(lotteryRepository).findByTicket(ticket);
        verify(lotteryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Non Zero amount of Lottery should return List of Ticket")
    public void TestGetLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new LotteryModel("123456", 80, 1),
                new LotteryModel("123457", 80, 1)
        ));

        List<String> result = lotteryService.getLotteries();

        assertEquals(Arrays.asList("123456", "123457"), result);

        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Zero amount of Lottery should return Empty List")
    public void TestGetZeroLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new LotteryModel("123456", 80, 0),
                new LotteryModel("123457", 80, 0)
        ));

        List<String> result = lotteryService.getLotteries();

        assertEquals(Collections.emptyList(), result);
        assertNotNull(result);

        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Initial state should return Empty List")
    public void TestGetInitLottery() {
        when(lotteryRepository.findAll()).thenReturn(Collections.emptyList());

        List<String> result = lotteryService.getLotteries();

        assertEquals(Collections.emptyList(), result);
        assertNotNull(result);

        verify(lotteryRepository).findAll();
    }
}
