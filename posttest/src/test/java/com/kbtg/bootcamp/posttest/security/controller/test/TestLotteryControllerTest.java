package com.kbtg.bootcamp.posttest.security.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
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

    @Autowired
    LotteryService lotteryService;

    @Test
    void shouldNotReturnHttpStatusOK() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldNotReturnStatusOk() {
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
    @DisplayName("EXP04 test:shouldReturn Status OK")
    void testEXP04p1() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP05 : shouldReturnStatus OK")
    void testEXP05p0() {

        ResponseEntity<Void> response =
                restTemplate.exchange("/test/1234567890/lotteries/555555",
                        HttpMethod.DELETE,
                        null,
                        Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when selling back a non-existent lottery ticket")
    void sellingBackNonExistentLotteryTicket() {
        // Arrange
        String nonExistentTicketId = "999999"; // this ticket doesn't exist

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                "/test/1234567890/lotteries/" + nonExistentTicketId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("EXP05 : Should return the lottery associated with the given user ID")
    void shouldReturnLotteryForExistingUserId() {
        // Arrange
        String requestedUserID = "1234567890"; // Existing user ID
        String requestedTicketId = "111111"; // Existing ticket ID


        // Act
        ResponseEntity<Void> response = lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID,
                requestedTicketId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Add assertions to verify the returned lottery
    }




}