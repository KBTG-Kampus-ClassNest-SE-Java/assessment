package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.models.LotteriesResponse;
import com.kbtg.bootcamp.posttest.services.LotteryService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {
    @Mock
    private LotteryService lotteryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Test list lotteries")
    public void listLotteries() throws Exception {
        var lotteries = new LotteriesResponse(List.of("111111", "222222"));

        when(lotteryService.listLotteries()).thenReturn(lotteries);
        mockMvc.perform(get("/lotteries")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(2)));
    }
}
