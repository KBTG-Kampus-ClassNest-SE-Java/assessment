package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryController;
import com.kbtg.bootcamp.posttest.response.UserTicketDetailResponse;
import com.kbtg.bootcamp.posttest.user.PublicController;
import com.kbtg.bootcamp.posttest.user.PublicService;
import com.kbtg.bootcamp.posttest.user.UserTicket;
import com.kbtg.bootcamp.posttest.user.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublicRequestTest {

    MockMvc mockMvc;
    @Mock
    PublicService publicService;

    @BeforeEach
    void Setup() {
        PublicController publicController = new PublicController(publicService);
        mockMvc = MockMvcBuilders.standaloneSetup(publicController)
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("when perform on post: /users/:user_id/lotteries/:lottery_id should return ticket id")
    void buyLotterySuccessShouldReturnIdOfUserTicket() throws Exception {
        String userId = "0000000002";
        Integer ticketId = 6;

        Users user = new Users();
        Lottery lottery = new Lottery();
        UserTicket userTicket = new UserTicket();
        userTicket.setUsers(user);
        userTicket.setLottery(lottery);
        userTicket.setId(1);

        Integer expectedValue = userTicket.getId();

        when(publicService.buyLottery(eq(userId), eq(ticketId))).thenReturn(Map.of("id", expectedValue));

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedValue)));
    }

    @Test
    @DisplayName("when perform on GET: /users/:userId/lotteries should return json response of user ticket")
    void getJsonResponseOfUserTicket() throws Exception {
        String actualUserId = "0000000002";
        UserTicketDetailResponse userTicketDetailResponse = new UserTicketDetailResponse();
        userTicketDetailResponse.setTickets(List.of("000001", "000002", "123456"));
        userTicketDetailResponse.setCount(3);
        userTicketDetailResponse.setCost(280);


        when(publicService.getUserTicket(eq(actualUserId))).thenReturn(userTicketDetailResponse);

        List<String> expectTicketVal = List.of("000001", "000002", "123456");
        Integer expectCountVal = expectTicketVal.size();
        Integer expectCostVal = 280;

        mockMvc.perform(get("/users/{userId}/lotteries", actualUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(expectTicketVal)))
                .andExpect(jsonPath("$.count", is(expectCountVal)))
                .andExpect(jsonPath("$.cost", is(expectCostVal)));
    }

    @Test
    @DisplayName("when perform on GET: /users/:userId/lotteries in case no data should return empty list")
    void getJsonResponseOfUserTicketNoUserTicket() throws Exception {
        String actualUserId = "0000000002";
        UserTicketDetailResponse userTicketDetailResponse = new UserTicketDetailResponse();
        userTicketDetailResponse.setTickets(List.of());
        userTicketDetailResponse.setCount(0);
        userTicketDetailResponse.setCost(0);

        when(publicService.getUserTicket(eq(actualUserId))).thenReturn(userTicketDetailResponse);

        List<String> expectTicketVal = List.of();
        Integer expectCountVal = 0;
        Integer expectCostVal = 0;

        mockMvc.perform(get("/users/{userId}/lotteries", actualUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(expectTicketVal)))
                .andExpect(jsonPath("$.count", is(expectCountVal)))
                .andExpect(jsonPath("$.cost", is(expectCostVal)));
    }

    @Test
    @DisplayName("when perform on DELETE: /users/:userId/lotteries/:ticketId should return json response key of delete ticket")
    void deleteUserTicketShouldReturnTicket() throws Exception {

        String userId = "0000000002";
        Integer ticketId = 1;

        Users user = new Users();
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(1);
        lottery.setPrice(80);

        String actualTicketDelete = lottery.getTicket();


        when(publicService.deleteUserTicket(eq(userId), eq(ticketId))).thenReturn(Map.of("ticket", actualTicketDelete));

        String expectedValue = "123456";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is(expectedValue)));
    }

}
