package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class DatabaseAccessTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveAndGetEntity() {
        // entity
        ResponseEntity<String> response =
                restTemplate.getForEntity("/test",String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
