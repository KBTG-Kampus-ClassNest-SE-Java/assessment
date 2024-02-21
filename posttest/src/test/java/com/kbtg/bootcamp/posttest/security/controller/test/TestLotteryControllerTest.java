package com.kbtg.bootcamp.posttest.security.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("EXP04 test: get all lottery ticket by user")
    void testEXP04p3() throws JsonProcessingException {
        List<Lottery> all = lotteryRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedBody = objectMapper.writeValueAsString(all);

        ResponseEntity<String> result = ResponseEntity.ok().body(expectedBody);
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test/1234567890/lotteries",String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(result.getBody());
    }

    @Test
    void shouldNotReturnStatusOk() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP04 test:shouldReturn Status OK")
    void testEXP04p1() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}