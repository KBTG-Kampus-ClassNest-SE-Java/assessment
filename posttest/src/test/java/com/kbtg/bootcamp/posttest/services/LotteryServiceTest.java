package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryServiceImpl lotteryService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Given lottery, when create lottery, then the lottery is created")
    void givenLottery_whenCreateLottery_thenTheLotteryIsCreated() {
        CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest("123456", 80, 1);

        Lottery newLottery = this.lotteryService.createLottery(createLotteryRequest);

        assertNotNull(newLottery);
        assertEquals("123456", newLottery.getTicket());
        assertEquals(80, newLottery.getPrice());
        assertEquals(1, newLottery.getAmount());
    }

    @Test
    @DisplayName("When lotteries size is greater than 0, then return lotteries")
    void whenLotteriesSizeIsGreaterThan0_thenReturnLotteries() {
        when(this.lotteryRepository.findAll()).thenReturn(List.of(
            Lottery.builder()
                    .ticket("123456")
                    .price(80)
                    .amount(1)
                    .build(),
            Lottery.builder()
                    .ticket("000567")
                    .price(100)
                    .amount(2)
                    .build()
        ));

        List<Lottery> lotteries = this.lotteryService.getLotteries();

        assertNotNull(lotteries);
        assertEquals(2, lotteries.size());

        assertEquals("123456", lotteries.get(0).getTicket());
        assertEquals(80, lotteries.get(0).getPrice());
        assertEquals(1, lotteries.get(0).getAmount());

        assertEquals("000567", lotteries.get(1).getTicket());
        assertEquals(100, lotteries.get(1).getPrice());
        assertEquals(2, lotteries.get(1).getAmount());
    }

    @Test
    @DisplayName("When lotteries size is 0, then return lotteries is not null")
    void whenLotteriesSizeIs0_thenReturnLotteriesIsNotNull() {
        when(this.lotteryRepository.findAll()).thenReturn(List.of());

        List<Lottery> lotteries = this.lotteryService.getLotteries();

        assertNotNull(lotteries);
        assertEquals(0, lotteries.size());
    }
}