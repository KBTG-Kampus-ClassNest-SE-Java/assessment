package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class LotteryServiceTest {


    @BeforeEach
    void setUp() {
        LotteryRepository lotteryRepository = mock(LotteryRepository.class); // Mocking the repository
        LotteryService lotteryService = new LotteryService(lotteryRepository); // Instantiating service with mocked repository
        LotteryController lotteryController = new LotteryController(lotteryService);
    }

    @Test
    @DisplayName("Lottery should have 6 digit of number")
    void testLotteryNumberLength() throws Exception {
        String testLottery = "123456";
        Integer amountTest = 1;
        Integer priceTest = 80;
    }
}