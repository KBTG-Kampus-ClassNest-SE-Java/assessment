package com.kbtg.bootcamp.posttest.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AdminControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldNotReturnHttpStatusOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin","password");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/admin", HttpMethod.GET, request, String.class);
        // make failed test because wrong username
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}