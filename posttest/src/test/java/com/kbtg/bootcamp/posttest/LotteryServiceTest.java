package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LotteryServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("get all lotteries should return list of ticket with an amount greater than or equal to 1")
    void testGetAllLotteries() {

        List<Lottery> mockLotteries = Arrays.asList(
                new Lottery(1, "123222", 1.0, 80),
                new Lottery(2, "456333", 1.0, 80),
                new Lottery(3, "789444", 0.5, 80)
        );

        when(lotteryRepository.findAll()).thenReturn(mockLotteries);

        LotteryListResponseDto response = lotteryService.getAllLotteries();

        List<String> expectedTickets = mockLotteries.stream()
                .filter(lottery -> lottery.getAmount() >= 1)
                .map(Lottery::getTicket)
                .collect(Collectors.toList());

        assertEquals(expectedTickets, response.getTickets());
    }
}
