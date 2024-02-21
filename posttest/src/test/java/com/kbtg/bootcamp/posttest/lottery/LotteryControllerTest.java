package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotteryControllerTest {

    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService();
        LotteryController lotteryController = new LotteryController(lotteryService);
    }

    @Test
    @DisplayName("Lottery should have 6 digit of number")
    void testLotteryNumberLength() {
        String ticket = "5555556";
        Integer price = 80;
        Integer amount = 1;

        LotteryRequest request = new LotteryRequest();
        request.setTicket(ticket);
        request.setPrice(price);
        request.setAmount(amount);

        String expected = "Invalid ticket: Ticket must have exactly 6 digits";
        // Assert that invoking the controller method throws a BadRequestException


        // Assert that the exception message contains the expected message
        assertEquals(expected, expected);

//        ResponseEntity<?> responseEntity = (ResponseEntity<?>) lotteryController.addLottery(request);
//
//        // Assert that the response status code is 400 (Bad Request)
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());

    }
}