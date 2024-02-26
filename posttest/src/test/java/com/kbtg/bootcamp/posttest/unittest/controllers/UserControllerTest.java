package com.kbtg.bootcamp.posttest.unittest.controllers;

import com.kbtg.bootcamp.posttest.configs.SecurityConfig;
import com.kbtg.bootcamp.posttest.controllers.UserController;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.exceptions.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.services.UserTicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserTicketService userTicketService;

    @Test
    @DisplayName("When buy lottery with exist ticket id then response correctly with status ok")
    void whenBuyLotteryWithExistTicketId_thenResponseCorrectlyWithStatusOk() throws Exception {
        when(this.userTicketService.buyLottery(anyString(), eq("123456"))).thenReturn(
                UserTicket
                        .builder()
                        .id(1)
                        .userId("2222233334")
                        .lottery(
                                Lottery.builder()
                                        .ticket("123456")
                                        .build()
                        )
                        .amount(1)
                        .build()
        );

        this.mvc.perform(post("/users/2222233334/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("When buy lottery with not exist ticket id then response with status not found")
    void whenBuyLotteryWithNotExistTicketId_thenResponseWithStatusNotFound() throws Exception {
        when(this.userTicketService.buyLottery(anyString(), eq("987654"))).thenThrow(LotteryNotFoundException.class);

        this.mvc.perform(post("/users/2222233334/lotteries/987654"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When buy lottery that is already sold out then response with status not found")
    void whenBuyLotteryThatIsAlreadySoldOut_thenResponseWithStatusNotFound() throws Exception {
        when(this.userTicketService.buyLottery(anyString(), eq("123456"))).thenThrow(LotterySoldOutException.class);

        this.mvc.perform(post("/users/2222233334/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When buy lottery with invalid ticket id then response with status bad request")
    void whenBuyLotteryWithInvalidTicketId_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(post("/users/2222233334/lotteries/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When buy lottery with invalid user id then response with status bad request")
    void whenBuyLotteryWithInvalidUserId_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(post("/users/invalid/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When get lottery details then response lottery details with status ok")
    void whenGetLotteryDetails_thenResponseLotteryDetailsWithStatusOk() throws Exception {
        GetLotteriesByUserIdResponse expectedResponse = new GetLotteriesByUserIdResponse(
                List.of("123456", "567890"),
                2,
                160
        );

        when(this.userTicketService.getLotteriesByUserId(anyString())).thenReturn(expectedResponse);

        this.mvc.perform(get("/users/2222233334/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets").value(containsInAnyOrder("123456", "567890")))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.cost").value(160));
    }

    @Test
    @DisplayName("When get lottery details with invalid user id then response with status bad request")
    void whenGetLotteryDetailsWithInvalidUserId_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(get("/users/invalid/lotteries"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When sell lottery then response ticket id with status ok")
    void whenSellLottery_thenResponseTicketIdWithStatusOk() throws Exception {
        when(this.userTicketService.sellLottery(anyString(), anyString())).thenReturn("123456");

        this.mvc.perform(delete("/users/2222233334/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").isString())
                .andExpect(jsonPath("$.ticket").value("123456"));
    }

    @Test
    @DisplayName("When sell lottery and lottery service throw UserTicketNotFoundException then response status is not found")
    void whenSellLotteryAndLotteryServiceThrowUserTicketNotFoundException_thenResponseStatusIsNotFound() throws Exception {
        when(this.userTicketService.sellLottery(anyString(), anyString())).thenThrow(UserTicketNotFoundException.class);

        this.mvc.perform(delete("/users/2222233334/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When sell lottery with invalid user id then response with status bad request")
    void whenSellLotteryWithInvalidUserId_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(delete("/users/invalid/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When sell lottery with invalid ticket id then response with status bad request")
    void whenSellLotteryWithInvalidTicketId_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(delete("/users/2222233334/lotteries/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}