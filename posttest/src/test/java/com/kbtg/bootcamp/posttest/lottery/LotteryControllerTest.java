package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    @WithMockUser(roles = "USER")
    @DisplayName("USER on GET:/lotteries should return List of ticket")
    void ViewLotterbyUser() throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(10);
        lottery.setPrice(100.0f);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket[0]").value("123456"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("ADMIN on GET:/lotteries should return List of ticket")
    void ViewLotterybyAdmin() throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(10);
        lottery.setPrice(100.0f);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket[0]").value("123456"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Public on GET:/lotteries should return status 401")
    void ViewLotterybyPublic() throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(10);
        lottery.setPrice(100.0f);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("ADMIN on POST:/lotteries should return ticket")
    void CreateLotteryAdmin() throws Exception {
        LotteryRequestDto requestDto = new LotteryRequestDto("777777", 14, 60.0f);
        String expectedResponse = "777777";

        when(lotteryService.createLottery(any())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").value(expectedResponse));
    }
}