package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    LotteryService lotteryService;

    @Autowired
    UserController userController;


    @Test
    void shouldNotReturnStatusOk() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP02 task: should return not null")
    void shouldReturnNonNull() {
        List<Lottery> allLotteries = lotteryService.getAllLotteries();
        assertThat(allLotteries).isNotNull();
    }

    @Test
    @DisplayName("EXP02 task: should return UserResponse")
    void shouldReturnUserResponse() {

        // arrange
        UserResponse userResponse = userController.getLotteriesPage();
        List<Lottery> allLotteries = lotteryService.getAllLotteries();
        // act
        List<String> expectedTickets = allLotteries.stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());

        // assert
        assertThat(userResponse.getTickets()).isEqualTo(expectedTickets);
    }
}