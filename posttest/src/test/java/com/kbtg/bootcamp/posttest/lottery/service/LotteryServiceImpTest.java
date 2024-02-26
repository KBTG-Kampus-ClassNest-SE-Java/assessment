package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.exception.LotteryException;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryListResDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceImpTest {
    @Mock
    private LotteryRepo lotteryRepo;

    @InjectMocks
    private LotteryServiceImp lotteryServiceImp;

    @Test
    void testCreateLottery_NewLottery_Success() {

        LotteryRequestDto requestDto = new LotteryRequestDto("123456", 10, 100);
        when(lotteryRepo.findById("123456")).thenReturn(Optional.empty());
        when(lotteryRepo.save(any(Lottery.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LotteryResponseDto responseDto = lotteryServiceImp.createLottery(requestDto);

        assertEquals("123456", responseDto.getTicket());
        verify(lotteryRepo, times(1)).findById("123456");
        verify(lotteryRepo, times(1)).save(any(Lottery.class));
    }


    @Test
    void testCreateLottery_ExistingLottery_ExceptionThrown() {

        LotteryRequestDto requestDto = new LotteryRequestDto("123456", 10, 100);
        when(lotteryRepo.findById("123456")).thenReturn(Optional.of(new Lottery()));

        assertThrows(LotteryException.class, () -> lotteryServiceImp.createLottery(requestDto));
        verify(lotteryRepo, times(1)).findById("123456");
        verify(lotteryRepo, never()).save(any(Lottery.class));
    }

    @Test
    public void testListAllLotteries() {

        List<Lottery> mockLotteries = Arrays.asList(new Lottery("123456", 10, 100), new Lottery("234567", 20, 100));
        when(lotteryRepo.findAll()).thenReturn(mockLotteries);

        LotteryListResDto responseDto = lotteryServiceImp.listAllLotteries();

        assertEquals(2, responseDto.getTickets().size());
        assertEquals(mockLotteries.stream().map(Lottery::getTicket).collect(Collectors.toList()), responseDto.getTickets());
    }

}
