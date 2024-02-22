package com.kbtg.bootcamp.posttest.lottery.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.kbtg.bootcamp.posttest.lottery.responese.LotteryListResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

  MockMvc mockMvc;
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
  @DisplayName("when preform on GET /lotteries, should be returnee 200 list of lotteries")
  void getLotteries() throws Exception{
    LotteryListResponse lotteries = new LotteryListResponse();
    lotteries.setTickets(List.of("0000001", "0000002", "0000003"));

    when(lotteryService.getLotteries()).thenReturn(lotteries);

    mockMvc.perform(get("/lotteries"))
        .andExpect(jsonPath("$.data.tickets[0]", is("0000001")))
        .andExpect(jsonPath("$.data.tickets[1]", is("0000002")))
        .andExpect(jsonPath("$.data.tickets[2]", is("0000003")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("Get lotteries success.")));
  }


}