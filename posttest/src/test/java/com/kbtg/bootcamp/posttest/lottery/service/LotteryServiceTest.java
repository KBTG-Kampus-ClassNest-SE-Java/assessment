package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    public void setUp() {
        lotteryTicketRequest = new LotteryTicketRequest();
        lotteryTicket = new LotteryTicket();
    }

    @ParameterizedTest
    @CsvSource({
            "123456, 80, 1",
            "000000, 1, 10",
            "100000, 100, 100",
    })
    @DisplayName("When create a new valid lottery ticket should return valid ticket number")
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
}
