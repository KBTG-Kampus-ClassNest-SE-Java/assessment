package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(LotteryController.class)
class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Test
    public void testAddLotteryTicket() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 80, \"amount\": 1}";
        LotteryTicketResponse lotteryTicketResponse = new LotteryTicketResponse("123456");

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenReturn(lotteryTicketResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").value(lotteryTicketResponse.ticket()));
    }

}
