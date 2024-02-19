package com.kbtg.bootcamp.posttest.security.controller;

import com.kbtg.bootcamp.posttest.lottery.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AdminControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    LotteryService lotteryService;

    @Autowired
    LotteryRepository lotteryRepository;

    @Test
    void shouldNotReturnHttpStatusOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user","password");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/admin", HttpMethod.GET, request, String.class);
        // make failed test because wrong username
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnEntity() {
        List<Lottery> allLotteries = lotteryService.getAllLotteries();
        assertThat(allLotteries).isNotNull();
    }

    @Test
    void shouldReturnListOfEntity() {
        List<Lottery> allLotteries = lotteryService.getAllLotteries();
        assertThat(allLotteries.size()).isEqualTo(4); // 4 -> right size
    }

    @Test
    @DisplayName("EXP01 task: check Duplicate")
    void shouldReturnDuplicateRuntime() {
        AdminRequest request = new AdminRequest("444444",60.0, 5L);
        assertThrows(DuplicateTickerException.class, () -> {
            lotteryService.createLottery(request);
        });
    }

    @Test
    @DisplayName("EXP01 task")
    void shouldReturnCreatedNewLotteryRequest() {
        Lottery lottery = new Lottery("879432", 55.5, 66L);

        AdminRequest request = new AdminRequest(lottery.getTicket(), lottery.getPrice(), lottery.getAmount());
        if (lottery.getId() == null) {
            LotteryResponse response = lotteryService.createLottery(request);
            assertThat(response).isNotNull();
            assertThat(response.getTicket()).isEqualTo(lottery.getTicket());
        } else {
            // not a new Lottery
            assertThat(lottery.getId()).isNotNull();
        }
    }
}