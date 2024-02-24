package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

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

    @Test
    @DisplayName("Given user id and ticket id, when buy lottery, then the lottery is bought and return user ticket")
    void givenUserIdAndTicketId_whenBuyLottery_thenReturnUserTicket() {
        when(this.lotteryRepository.findById(any())).thenReturn(Optional.of(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(100)
                        .amount(2)
                        .build()
        ));

        when(this.userTicketRepository.existsByUserIdAndTicketId(any(), any())).thenReturn(true);

        when(this.userTicketRepository.updateTicketAmountOfUser(any(), any(), eq(1))).thenReturn(
                UserTicket
                        .builder()
                        .userId("username")
                        .ticketId("123456")
                        .amount(1)
                        .build()
        );

        UserTicket userTicket = this.lotteryService.buyLottery("username", "123456");

        assertNotNull(userTicket);
        assertEquals("username", userTicket.getUserId());
        assertEquals("123456", userTicket.getTicketId());
        assertTrue(userTicket.getAmount() > 0);
    }

    @Test
    @DisplayName("Given user id and ticket id, when lottery is not found, then throw lottery not found exception")
    void givenUserIdAndTicketId_whenLotteryIsNotFound_thenThrowLotteryNotFoundException() {
        when(this.lotteryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(LotteryNotFoundException.class, () -> {
            this.lotteryService.buyLottery("username", "123456");
        });
    }

    @Test
    @DisplayName("Given user id and ticket id, when lottery is found but sold out, then throw lottery sold out exception")
    void givenUserIdAndTicketId_whenLotteryIsFoundButSoldOut_thenThrowLotterySoldOutException() {
        when(this.lotteryRepository.findById(any())).thenReturn(Optional.of(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(100)
                        .amount(0)
                        .build()
        ));

        assertThrows(LotterySoldOutException.class, () -> {
            this.lotteryService.buyLottery("username", "123456");
        });
    }
}