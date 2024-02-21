package com.kbtg.bootcamp.posttest.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


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

    @Autowired
    ProfileRepository  profileRepository;

    @Autowired
    LotteryRepository lotteryRepository;



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

        ResponseEntity<String> result = ResponseEntity.ok().body("test");
        ResponseEntity<String> response =
                restTemplate.postForEntity("/test/1/lotteries/111111",request, String.class );

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(result.getBody());
    }

    @Test
    @DisplayName("EXP03 test: shouldReturn Body with Profile")
    void test2() {
        UserRequest request = new UserRequest("1234567890","111111");
        Profile profile = new Profile();
        profile.setUserId("1234567890");
        ResponseEntity<Profile> result = ResponseEntity.ok().body(profile);
        ResponseEntity<Profile> response =
                restTemplate.postForEntity("/users/1/lotteries/111111",request, Profile.class );

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(Profile.class);
    }

    @Test
    @DisplayName("EXP03 test: shouldReturn Body with Id from user_ticket ")
    void test3() {
        UserRequest request = new UserRequest("1234567890","111111");
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("id", request.userId());
        ResponseEntity<?> result = ResponseEntity.ok().body(responseBody);
        ResponseEntity<String> response =
                restTemplate.postForEntity("/users/1/lotteries/111111",request, String.class );

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    @DisplayName("EXP04 test:shouldReturn Status OK with correct path variable")
    void testEXP04p2() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/users/1234567890/lotteries",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



}