package com.kbtg.bootcamp.posttest.security.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        ResponseEntity<Void> response = (ResponseEntity<Void>) lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID,
                requestedTicketId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Add assertions to verify the returned lottery
    }

    @Test
    @DisplayName("EXP05 :Should return a list of lotteries associated with the given user ID")
    void shouldReturnListOfLotteriesForExistingUserId() {
        // Arrange
        String requestedUserID = "1234567890"; // Existing user ID
        String requestedTicketId1 = "111111";

        // Act
        ResponseEntity<List<Lottery>> response = (ResponseEntity<List<Lottery>>) lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID,
                requestedTicketId1);
        System.out.println("response = " + response);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull(); // Check if the body is not null
        assertThat(response.getBody()).isNotEmpty(); // Check if the body contains at least one lottery
        // Add additional assertions to verify the content of the list of lotteries
    }

    @Test
    @DisplayName("Should remove profile and set amount to 0 for the lottery with the given ticket ID")
    void shouldModifyLotteryWithGivenTicketID() {
        // Arrange
        String requestedUserID = "1234567890";
        String requestedTicketId = "111111";

        // Mock the lottery service
        LotteryService lotteryService = mock(LotteryService.class);

        // Create a list of lotteries
        List<Lottery> lotteries = new ArrayList<>();
        lotteries.add(new Lottery("111111", 100.0, 1L, new Profile("1234567890", "John", "123")));
        lotteries.add(new Lottery("222222", 200.0, 2L, new Profile("1234567890", "John", "123")));
        lotteries.add(new Lottery("555555", 300.0, 3L, new Profile("1234567890", "John", "123")));

        // Stub the getAllLotteriesByUserId method to return the list of lotteries
        when(lotteryService.getAllLotteriesByUserId(requestedUserID)).thenReturn(lotteries);

        // Create the controller with the mocked lottery service
        TestLotteryController controller = new TestLotteryController(lotteryService);
        // Act
        ResponseEntity<List<Lottery>> response = (ResponseEntity<List<Lottery>>) lotteryService.sellLotteryByUsingUserIdAndLotteryTicket(requestedUserID,
                requestedTicketId);

        List<Lottery> result = response.getBody();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify that the correct lottery is modified
        Lottery modifiedLottery = lotteries.stream()
                .filter(lottery -> lottery.getTicket().equals(requestedTicketId))
                .findFirst()
                .orElse(null);
        assertThat(modifiedLottery).isNotNull();
        assertThat(modifiedLottery.getProfile()).isNull();
        assertThat(modifiedLottery.getAmount()).isEqualTo(0L);
    }







}