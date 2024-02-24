package com.kbtg.bootcamp.posttest.user;

import com.jayway.jsonpath.JsonPath;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
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
                restTemplate.getForEntity("/users/0987654321/lotteries",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("EXP04 : return List of lottery that existedUserId have")
    void testEXP04p3() {
        boolean userExistsByUserId = lotteryService.isUserExistsByUserId("0987654321");
        System.out.println("userExistsByUserId = " + userExistsByUserId);

    }

    @Test
    @DisplayName("Exp05: return ")
    void testEp05() {

    }







}