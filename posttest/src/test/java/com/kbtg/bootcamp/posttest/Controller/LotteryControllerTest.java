package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

    private MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("GET:/lotteries should return Empty List and status ok - case no ticket")
    public void TestGetLotteriesEmpty() throws Exception {
        when(lotteryService.getLottery()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets", hasSize(0)));
    }

    @Test
    @DisplayName("GET:/lotteries should return List of ticket and status ok - case one ticket")
    public void TestGetLotteries() throws Exception {
        Lottery lottery = new Lottery("123456", 10L, 100.0);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets", hasSize(1)))
                .andExpect(jsonPath("$.tickets[0]", is("123456")));
    }

    @Test
    @DisplayName("GET:/lotteries should return List of ticket and status ok - case multiple ticket")
    public void TestGetLotteriesMultiple() throws Exception {
        Lottery lottery = new Lottery("123456", 10L, 100.0);
        Lottery lottery2 = new Lottery("654321", 10L, 100.0);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket(), lottery2.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets", hasSize(2)))
                .andExpect(jsonPath("$.tickets[0]", is("123456")))
                .andExpect(jsonPath("$.tickets[1]", is("654321")));
    }
}