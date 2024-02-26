package com.kbtg.bootcamp.posttest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
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

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private LotteryService lotteryService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Test add lottery")
    public void addLottery() throws Exception {
        var lottery = new Lottery("111111", BigDecimal.valueOf(80), 1);
        var lotteryResponse = new LotteryResponse(lottery.getTicket());

        when(lotteryService.addLottery(lottery)).thenReturn(lotteryResponse);
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lottery)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value(lottery.getTicket()));
    }
}
