package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.configs.SecurityConfig;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

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
    @DisplayName("When buy lottery with exist ticket id then response correctly with status ok")
    void whenBuyLotteryWithExistTicketId_thenResponseCorrectlyWithStatusOk() throws Exception {
        when(this.lotteryService.buyLottery(any(), any())).thenReturn(
                UserTicket
                        .builder()
                        .id(1)
                        .userId("username")
                        .ticketId("123456")
                        .amount(1)
                        .build()
        );

        this.mvc.perform(
            post("/users/username/lotteries/123456")
                    .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("When buy lottery with not exist ticket id then response with status not found")
    void whenBuyLotteryWithNotExistTicketId_thenResponseWithStatusNotFound() throws Exception {
        when(this.lotteryService.buyLottery(any(), eq("987654"))).thenThrow(LotteryNotFoundException.class);

        this.mvc.perform(
                post("/users/username/lotteries/987654")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When buy lottery that is already sold out then response with status not found")
    void whenBuyLotteryThatIsAlreadySoldOut_thenResponseWithStatusNotFound() throws Exception {
        when(this.lotteryService.buyLottery(any(), eq("123456"))).thenThrow(LotterySoldOutException.class);

        this.mvc.perform(
                post("/users/username/lotteries/123456")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}