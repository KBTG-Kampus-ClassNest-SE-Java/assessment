package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.configs.SecurityConfig;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(LotteryController.class)
@WithAnonymousUser
class LotteryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("When lotteries service returns non-empty list then response list of tickets with status ok")
    void whenLotteryServiceReturnNonEmptyList_thenResponseLotteriesCorrectlyWithStatusOk() throws Exception {
        when(this.lotteryService.getLotteries()).thenReturn(List.of(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(80)
                        .amount(1)
                        .build(),
                Lottery
                        .builder()
                        .ticket("453143")
                        .price(100)
                        .amount(2)
                        .build()
        ));

        this.mvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets.length()").value(3))
                .andExpect(jsonPath("$.tickets").value(containsInAnyOrder("123456", "453143", "453143")));
    }

    @Test
    @DisplayName("When lotteries service returns empty list then return empty list")
    void whenLotteryServiceReturnsEmptyList_thenResponseWithEmptyListWithStatusOk() throws Exception {
        when(this.lotteryService.getLotteries()).thenReturn(List.of());

        this.mvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets.length()").value(0));
    }
    
}