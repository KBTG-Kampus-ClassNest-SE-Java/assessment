package com.kbtg.bootcamp.posttest.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldNotReturnStatusOk() {
        ResponseEntity<String> response =
            restTemplate.getForEntity("/users", String.class);
    }
}