package com.kbtg.bootcamp.posttest.security.controller.test;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TestLotteryControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    LotteryRepository lotteryRepository;

    @Test
    void shouldNotReturnHttpStatusOK() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldNotReturnEntity() {
        Lottery lottery = lotteryRepository.findById(1L).orElseThrow(RuntimeException::new); // right id
        assertThat(lottery).isNotNull();
    }

    @Test
    void shouldReturnEntityWithCorrectTicketNumber() {
        Lottery lottery = lotteryRepository.findById(1L).orElseThrow(RuntimeException::new); // right id
        String lotteryTicket = lottery.getTicket();
        assertThat(lotteryTicket).isEqualTo("111111"); // right ticketnumber
    }
}