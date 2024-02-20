package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketListResponse;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class LotteryServiceTest {
    private LotteryTicketRequest lotteryTicketRequest;
    private LotteryTicket lotteryTicket;

    @Mock
    private LotteryTicketRepository lotteryTicketRepository;

    @InjectMocks
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        lotteryTicketRequest = new LotteryTicketRequest();
        lotteryTicket = new LotteryTicket();
    }

    @ParameterizedTest
    @CsvSource({
            "123456, 80, 1",
            "000000, 1, 10",
            "100000, 100, 100",
    })
    @DisplayName("Creating a new valid lottery ticket, should return valid ticket number")
    void testCreateLotteryTicket(String ticketNumber, int price, int amount) {
        lotteryTicketRequest.setTicket(ticketNumber);
        lotteryTicketRequest.setPrice(price);
        lotteryTicketRequest.setAmount(amount);
        lotteryTicket.setTicket(ticketNumber);
        lotteryTicket.setPrice(price);
        lotteryTicket.setAmount(amount);

        when(lotteryTicketRepository.save(any())).thenReturn(lotteryTicket);
        LotteryTicketResponse lotteryTicketResponse = lotteryService.createLotteryTicket(lotteryTicketRequest);
        String actualResult = lotteryTicketResponse.ticket();

        assertEquals(ticketNumber, actualResult);
    }

    @Test
    @DisplayName("Should not create duplicate lottery ticket")
    void testShouldNotCreateDuplicateTicket() {
        lotteryTicketRequest.setTicket("123456");
        lotteryTicket.setTicket("123456");

        when(lotteryTicketRepository.findByTicket("123456")).thenReturn(lotteryTicket);
        LotteryTicketResponse actualResult = lotteryService.createLotteryTicket(lotteryTicketRequest);

        verify(lotteryTicketRepository, never()).save(any());
        assertEquals("123456", actualResult.ticket());
    }

    @Test
    @DisplayName("Getting the lottery ticket list, should return a valid ticket list")
    void testGetLotteryTicketList() {
        List<String> ticketNumber = List.of("123456", "000000");
        List<LotteryTicket> ticketList = new ArrayList<>();
        lotteryTicket.setTicket("123456");
        ticketList.add(lotteryTicket);
        LotteryTicket lotteryTicket2 = new LotteryTicket();
        lotteryTicket2.setTicket("000000");
        ticketList.add(lotteryTicket2);

        when(lotteryTicketRepository.findAll()).thenReturn(ticketList);
        LotteryTicketListResponse actualResult = lotteryService.getLotteryTicketList();

        assertEquals(ticketNumber, actualResult.tickets());
    }
}
