package com.kbtg.bootcamp.lottery;


import com.kbtg.bootcamp.lottery.controller.UserController;
import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryUserResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when user purchase lottery on POST: /api/wallets should return status 200 and body contain ticket id.")
    void createLottery() throws Exception {
        LotteryPurchaseResponseDto lottery = new LotteryPurchaseResponseDto(1);
        when(lotteryService.purchaseLotteryTicket("2024022100", "123456")
        ).thenReturn(lottery);
        mockMvc.perform(
                        post("/users/2024022100/lotteries/123456")
                )
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GET: /{userId}/lotteries should return status 200 and body contain list of lotteries from userId.")
    void viewLotteriesByUser() throws Exception {
        List<String> ticketNumbers = List.of("123456", "123457");
        BigDecimal totalPrice = new BigDecimal("160.0");
        Long totalTicket = 2L;
        LotteryUserResponseDto lotteryUser = new LotteryUserResponseDto(ticketNumbers, totalPrice, totalTicket);
        when(lotteryService.getUserLotteryTickets("2024022100")).thenReturn(lotteryUser);
        mockMvc.perform(
                        get("/users/2024022100/lotteries")
                )
                .andExpect(jsonPath("$.tickets", is(ticketNumbers))) // Correctly navigate to the first ticket
                .andExpect(jsonPath("$.cost", is(160.0))) // Verify the cost
                .andExpect(jsonPath("$.count", is(2))) // Verify the count
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GET: /{userId}/lotteries/{ticketId} should return status 200 and body contain list of lotteries from userId.")
    void deleteLottery() throws Exception {
        LotteryResponseDto lottery = new LotteryResponseDto("123456");
        when(lotteryService.soldBackLotteryTicket("2024022100","1")).thenReturn(lottery);

        mockMvc.perform(
                        delete("/users/2024022100/lotteries/1")
                )
                .andExpect(jsonPath("$.ticket", is("123456")))
                .andExpect(status().isOk());
    }
}