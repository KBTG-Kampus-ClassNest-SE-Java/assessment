package com.kbtg.bootcamp.posttest.Service;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Exception.ConflictException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.Lottery.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {
    @Mock
    private LotteryRepository lotteryRepository;
    @InjectMocks
    private LotteryService lotteryService;

    @Test
    @DisplayName("Initial state should return Empty List")
    public void TestgetInitLottery() {
        when(lotteryRepository.findAll()).thenReturn(Collections.emptyList());

        List<String> result = lotteryService.getLottery();

        assertEquals(Collections.emptyList(), result);
        assertNotNull(result);
        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Non Zero amount of Lottery should return List of Ticket")
    public void TestgetLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new Lottery("123456", 100L, 10.0),
                new Lottery("654321", 150L, 15.0)
        ));

        List<String> result = lotteryService.getLottery();

        assertEquals(Arrays.asList("123456", "654321"), result);
        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Create lottery should success and return ticket Id as string")
    public void TestcreateLotterySuccess() {
        String ticket = "123456";
        Long amount = 100L;
        double price = 10.0;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, amount, price);
        when(lotteryRepository.findFirstByTicket(ticket)).thenReturn(java.util.Optional.empty());

        String result = lotteryService.createLottery(lotteryRequestDto);

        assertEquals(ticket, result);
        verify(lotteryRepository).findFirstByTicket(ticket);
        verify(lotteryRepository).save(any(Lottery.class));
    }

    @Test
    @DisplayName("Create lottery that already exists should throw ConflictException")
    public void TestcreateDuplicateTicket() {
        String ticket = "123456";
        Long amount = 100L;
        double price = 10.0;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, amount, price);

        when(lotteryRepository.findFirstByTicket(ticket)).thenReturn(java.util.Optional.of(new Lottery()));

        assertThrows(ConflictException.class, () -> {
            lotteryService.createLottery(lotteryRequestDto);
        });
        verify(lotteryRepository).findFirstByTicket(ticket);
        verify(lotteryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete existing lottery should success")
    public void TestdeleteLotterySuccess() {
        String ticketID = "123456";
        String ticket = "123456";
        Long amount = 100L;
        double price = 10.0;

        Lottery lottery = new Lottery(ticket, amount, price);

        when(lotteryRepository.findById(Integer.parseInt(ticketID))).thenReturn(Optional.of(lottery));

        String result = lotteryService.deleteLottery(ticketID);

        assertEquals(ticket, result);
        verify(lotteryRepository).findById(Integer.parseInt(ticketID));
        verify(lotteryRepository).delete(lottery);
    }

    @Test
    @DisplayName("Delete non existing lottery should throw NotFoundException")
    public void TestdeleteLotteryNotFound() {
        int ticketID = 123456;

        when(lotteryRepository.findById(ticketID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            lotteryService.deleteLottery(String.valueOf(ticketID));
        });
        verify(lotteryRepository).findById(ticketID);
        verify(lotteryRepository, never()).delete(any());
    }
}