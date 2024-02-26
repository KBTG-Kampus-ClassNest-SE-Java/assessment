package com.kbtg.bootcamp.posttest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.models.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
import com.kbtg.bootcamp.posttest.models.MyLotteryResponse;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import com.kbtg.bootcamp.posttest.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserLotteryControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private LotteryService lotteryService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        UserLotteryController userLotteryController = new UserLotteryController(userService, lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(userLotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Test list all my lottery")
    public void listAllMyLottery() throws Exception {
        var userId = 1000000001;
        var myLotteryResponse = new MyLotteryResponse(List.of("000000", "000001"), 2, BigDecimal.valueOf(160));

        when(userService.listAllMyLottery(userId)).thenReturn(myLotteryResponse);
        mockMvc.perform(get("/users/" + userId + "/lotteries")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(2)))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.cost").value(160));
    }

    @Test
    @DisplayName("Test buy lottery")
    public void buyLottery() throws Exception {
        var userId = 1000000001;
        var ticketId = 1;
        var buyLotteryResponse = new BuyLotteryResponse(1);

        when(lotteryService.buyLottery(userId, ticketId)).thenReturn(buyLotteryResponse);
        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId));
    }

    @Test
    @DisplayName("Test sell back my lottery")
    public void sellBackMyLottery() throws Exception {
        var userId = 1000000001;
        var ticketId = 1;
        var ticket = "000000";
        var lotteryResponse = new LotteryResponse(ticket);

        when(lotteryService.sellBackMyLottery(userId, ticketId)).thenReturn(lotteryResponse);
        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").value(ticket));
    }
}
