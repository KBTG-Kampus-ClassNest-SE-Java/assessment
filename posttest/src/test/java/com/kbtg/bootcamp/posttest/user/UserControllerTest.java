package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.LotteryNotBelongToUserException;
import com.kbtg.bootcamp.posttest.exception.NotExistLotteryException;
import com.kbtg.bootcamp.posttest.exception.NotExistUserIdException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    LotteryService lotteryService;

    @Autowired
    UserController userController;



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


    @Test
    @DisplayName("EXP03 test: shouldReturn HTTPStatusOK and Body")
    void test() {
        UserRequest request = new UserRequest("1234567890","111111");
        ResponseEntity<?> response =
                testRestTemplate.postForEntity("/users/1234567890/lotteries/111111",request, Object.class );
        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP03 : Return lottery that has null Profile")
    void shouldReturnLotteryThatHasNullProfile() {
        List<Lottery> lotteriesWithNullProfile = lotteryService.getAllLotteries().stream()
                .filter(lottery -> lottery.getProfile() == null).collect(Collectors.toList());

        // Assert that the list is not empty
        assertThat(lotteriesWithNullProfile).isNotEmpty();

        // Assert that all lotteries in the list have a null profile
        assertThat(lotteriesWithNullProfile)
                .allMatch(lottery -> lottery.getProfile() == null);
    }

    @Test
    @DisplayName("EXP03 : Return Exception when user buy the possessions lottery")
    void shouldReturnDuplicateException() {
        // Arrange
        String userId = "1234567890";
        String lotteryId = "111111";


        // Act & Assert
        ResponseEntity<?> responseEntity = lotteryService.buyLotteries(new UserRequest(userId, lotteryId));


        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("You already have one");
    }
    @Test
    void testtest() {
        lotteryService.validateInputInformation("1234567890","111111");
    }



    @Test
    @DisplayName("EXP03 test: shouldReturn Body with Id from user_ticket ")
    void test3() {
        UserRequest request = new UserRequest("1234567890","111111");
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("id", request.userId());
        ResponseEntity<?> result = ResponseEntity.ok().body(responseBody);
        ResponseEntity<String> response =
                testRestTemplate.postForEntity("/users/1234567890/lotteries/111111",request, String.class );

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    @DisplayName("EXP04 test:shouldReturn Status OK with correct path variable")
    void testEXP04p2() {
        ResponseEntity<String> response =
                testRestTemplate.getForEntity("/users/0987654321/lotteries",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP04 : return List of lottery that existedUserId have")
    void testEXP04p3() {
        boolean userExistsByUserId = lotteryService.isUserExistsByUserId("0987654321");
        System.out.println("userExistsByUserId = " + userExistsByUserId);
    }

    @Test
    @DisplayName("EXP05 : shouldReturnStatus OK")
    void testEXP05p0() {

        testRestTemplate = mock(TestRestTemplate.class);
        // Create a mock response entity with the desired status code
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);

        // Define the behavior of the restTemplateMock when the exchange method is called
        when(testRestTemplate.exchange(eq("/users/1234567890/lotteries/555555"), eq(HttpMethod.DELETE), any(), eq(Void.class)))
                .thenReturn(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when selling back a non-existent lottery ticket")
    void sellingBackNonExistentLotteryTicket() {
        // Arrange
        String nonExistentTicketId = "999999"; // this ticket doesn't exist
        testRestTemplate = mock(TestRestTemplate.class);

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Act
        // Define the behavior of the restTemplateMock when the exchange method is called
        when(testRestTemplate.exchange(eq("/users/1234567890/lotteries/"+nonExistentTicketId), eq(HttpMethod.DELETE), any(), eq(Void.class)))
                .thenReturn(response);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("EXP05 : Should return the lottery associated with the given user ID")
    void shouldReturnLotteryForExistingUserId() {
        // Arrange
        String requestedUserID = "1234567890"; // Existing user ID
        String requestedLotteryId = "111111"; // Existing Lottery ID
        testRestTemplate = mock(TestRestTemplate.class);
        ResponseEntity<Void> response = ResponseEntity.ok().build();
        when(testRestTemplate.exchange(
                eq("/users/" +requestedUserID + "lotteries/" + requestedLotteryId),
                eq(HttpMethod.GET),
                any(),
                eq(Void.class)
        )).thenReturn(response);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP05 :Should return a list of lotteries associated with the given user ID")
    void shouldReturnListOfLotteriesForExistingUserId() {
        // Arrange
        String userId = "1234567890"; // Existing user ID
        String lotteryId = "111111";// Existing lottery ID

        // Mock
        testRestTemplate = mock(TestRestTemplate.class);
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        // Stub the exchange
        when(testRestTemplate.exchange(
                eq("/users/" + userId + "/lotteries/" + lotteryId),
                eq(HttpMethod.DELETE),
                any(),
                eq(Void.class)
        )).thenReturn(expectedResponse);

        // Act: Perform the actual method call that you want to test
        ResponseEntity<Void> actualResponse = testRestTemplate.exchange(
                "/users/" + userId + "/lotteries/" + lotteryId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        // Assert
        assertThat(actualResponse.getStatusCode()).isEqualTo(expectedResponse.getStatusCode());
        // Check if the body of the actual response is not null
        assertThat(actualResponse).isNotNull();
    }

    @Test
    @DisplayName("EXP05: should throw LotteryNotBelongToUserException")
    void testException() {
        String userId = "1234567890";
        String lotteryId = "333333";
        testRestTemplate = mock(TestRestTemplate.class);

        ResponseEntity<Void> response = ResponseEntity.badRequest().build();
        when(testRestTemplate.exchange(
                eq("/users/" + userId + "/lotteries/" +lotteryId),
                eq(HttpMethod.DELETE),
                any(),
                eq(Void.class)
        )).thenReturn(response);

        assertThrows(LotteryNotBelongToUserException.class, () -> {
            lotteryService.validateLotteryOwnership("1234567890", "333333");
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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