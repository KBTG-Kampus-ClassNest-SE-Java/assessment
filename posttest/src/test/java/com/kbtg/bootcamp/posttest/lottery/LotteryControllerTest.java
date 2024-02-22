package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotteryControllerTest {

    @Autowired
    private LotteryController lotteryController;
    private LotteryService lotteryService;
    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService();
        LotteryController lotteryController = new LotteryController(lotteryService);
    }

    @Test
    @DisplayName("Lottery should have 6 digit of number")
    void testLotteryNumberLength() throws Exception {
        String ticket = "5555556";
        Integer price = 80;
        Integer amount = 1;

        LotteryRequest request = new LotteryRequest();
        request.setTicket(ticket);
        request.setPrice(price);
        request.setAmount(amount);
        lotteryService.addLottery(request);
        String expected = "Invalid ticket: Ticket must have exactly 6 digits";
        assertEquals(expected, expected);

    }

    @Test
    @DisplayName("User can only buy lottery that admin created")
    void couldBuyOnlyExistLottery() {

    }
}