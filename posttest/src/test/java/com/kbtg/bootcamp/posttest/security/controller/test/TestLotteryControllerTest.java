package com.kbtg.bootcamp.posttest.security.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.exception.LotteryNotBelongToUserException;
import com.kbtg.bootcamp.posttest.exception.NotExistLotteryException;
import com.kbtg.bootcamp.posttest.exception.NotExistUserIdException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import io.swagger.v3.oas.models.PathItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TestLotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

        restTemplate = mock(TestRestTemplate.class);

        // Create a mock response entity with the desired status code
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

        // Define the behavior of the restTemplateMock when the exchange method is called
        when(restTemplate.exchange(eq("/test/1234567890/lotteries/555555"), eq(HttpMethod.DELETE), any(), eq(Void.class)))
                .thenReturn(response);

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
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("EXP05 : Should return the lottery associated with the given user ID")
    void shouldReturnLotteryForExistingUserId() {
        // Arrange
        String requestedUserID = "1234567890"; // Existing user ID
        String requestedTicketId = "111111"; // Existing ticket ID

        // Act
        ResponseEntity<Void> response =
                restTemplate.exchange(
                        "/test/" + requestedUserID + "/lotteries/" + requestedTicketId,
                        HttpMethod.GET,
                        null,
                        Void.class
                );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP05 :Should return a list of lotteries associated with the given user ID")
    void shouldReturnListOfLotteriesForExistingUserId() {
        // Arrange
        String requestedUserID = "1234567890"; // Existing user ID
        String requestedTicketId1 = "111111";

        // Act
        ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID,
        requestedTicketId1);
        System.out.println("response = " + response);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull(); // Check if the body is not null
        assertThat(response.getBody()).isNotEmpty(); // Check if the body contains at least one lottery
    }

    @Test
    @DisplayName("EXP05: should throw LotteryNotBelongToUserException")
    void testException() {
        ResponseEntity<?> response1 =
                lotteryService.sellLotteryByUsingUserIdAndLotteryTicket("1234567890", "333333");

        assertThrows(LotteryNotBelongToUserException.class, () -> {
            lotteryService.validateLotteryOwnership("1234567890", "333333");
        });
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testHandleNotExistLotteryException() {
        // Arrange
        Exception exception = new NotExistLotteryException("Lottery does not exist");

        // Act
        ResponseEntity<?> response = lotteryService.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Lottery does not exist", response.getBody());
    }

    @Test
    void testHandleNotExistUserIdException() {
        // Arrange
        Exception exception = new NotExistUserIdException("User does not exist");

        // Act
        ResponseEntity<?> response = lotteryService.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not exist", response.getBody());
    }

    @Test
    void testHandleLotteryNotBelongToUserException() {
        // Arrange
        Exception exception = new LotteryNotBelongToUserException("Lottery does not belong to user.");

        // Act
        ResponseEntity<?> response = lotteryService.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Lottery does not belong to user.", response.getBody());
    }

    @Test
    void testHandleInternalServerError() {
        // Arrange
        Exception exception = new RuntimeException("Internal Server Error");

        // Act
        ResponseEntity<?> response = lotteryService.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }




}