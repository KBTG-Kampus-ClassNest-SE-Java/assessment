package com.kbtg.bootcamp.posttest.Lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.Controller.LotteryController;
import com.kbtg.bootcamp.posttest.Entity.Lottery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

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
        lottery.setAmount(10L);
        lottery.setPrice(100.0);

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
        lottery.setAmount(10L);
        lottery.setPrice(100.0);

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
        lottery.setAmount(10L);
        lottery.setPrice(100.0);

        when(lotteryService.getLottery()).thenReturn(List.of(lottery.getTicket()));

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("ADMIN on POST:/lotteries should return ticket")
    void CreateLotteryAdmin() throws Exception {
        LotteryRequestDto requestDto = new LotteryRequestDto("777777", 14L, 60.0);
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