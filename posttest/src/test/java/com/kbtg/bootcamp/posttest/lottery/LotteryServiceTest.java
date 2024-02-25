package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exeption.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {

    MockMvc mockMvc;

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService(lotteryRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryService)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Lottery should have 6 digit of number")
    void testLotteryNumberLength() throws Exception {
        String testLottery = "123456";
        Integer amountTest = 1;
        Integer priceTest = 80;
    }

    @Test
    @DisplayName("When call getAllLotteries should return list of lottery that admin created")
    void showAllLotteryList() {
        Lottery lottery1 = new Lottery("123456", 10, 80);
        Lottery lottery2 = new Lottery("456456", 20, 70);
        List<Lottery> expectedLotteries = List.of(lottery1, lottery2);

        when(lotteryRepository.findAll()).thenReturn(expectedLotteries);

        // Act
        List<Lottery> actualLotteries = lotteryService.getAllLotteries();

        // Assert
        assertEquals(expectedLotteries, actualLotteries);
    }

    @Test
    @DisplayName("when call deleteAllLotteries list of lottery should empty")
    void listOfLotteryShouldEmptyAfterDeleteAll() {

        Lottery lottery1 = new Lottery("123456", 10, 80);
        Lottery lottery2 = new Lottery("456456", 20, 70);
        List<Lottery> expectedLotteries = List.of(lottery1, lottery2);

        lotteryRepository.findAll();

        lotteryService.deleteAllLotteries();

        assertTrue(lotteryService.getAllLotteries().isEmpty());
    }

    @Test
    @DisplayName("Add valid lottery should return response with lottery ID")
    void testAddValidLottery() throws Exception {
        // Arrange
        LotteryRequest request = new LotteryRequest();
        request.setTicket("123456");
        request.setPrice(10);
        request.setAmount(10);

        Lottery newLottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());
        when(lotteryRepository.save(any(Lottery.class))).thenReturn(newLottery);

        LotteryResponse response = lotteryService.addLottery(request);
        // Assert
        assertEquals(1, response.getLotteryIds().size());
        assertEquals("123456", response.getLotteryIds().get(0));
    }
}