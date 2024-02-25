package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.payload.LotteryListDetailResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
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
    UserService userService;
    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("[createUserAndLottery] Should be make user, lottery and Status CREATED (201)")
    void shouldCreateUserAndLottery() throws Exception {

        String userId = "1234567890";
        String ticketId = "123456";

//        UserIdResponseDto response = new UserIdResponseDto(1);

        when(userService.createUserAndLottery(userId, ticketId)).thenReturn("1");

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1")))
                .andReturn();
    }
    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with userId is not number")
    void shouldBadRequestWithUserIdNotNumber() throws Exception {

        String userId = "tenzeroten";
        String ticketId = "123456";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with userId is less then 10 digits")
    void shouldBadRequestWithUserIdLessThanTenDigits() throws Exception {

        String userId = "123456789";
        String ticketId = "123456";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with userId is more then 10 digits")
    void shouldBadRequestWithUserIdMoreThanTenDigits() throws Exception {

        String userId = "12345678901";
        String ticketId = "123456";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with ticketId is not number")
    void shouldBadRequestWithTicketIdNotNumber() throws Exception {

        String userId = "1234567890";
        String ticketId = "OneTwoThree";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with ticketId is less then 6 digits")
    void shouldBadRequestWithTicketIdLessThanSixDigits() throws Exception {

        String userId = "1234567890";
        String ticketId = "12345";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("[createUserAndLottery] Should be Bad request (400) with ticketId is more then 6 digits")
    void shouldBadRequestWithTicketIdMoreThanSixDigits() throws Exception {

        String userId = "1234567890";
        String ticketId = "1234567";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[getUserBuyLotteryDetail] Should be get user lottery detail (List of Lotteries, count and cost Status OK (200)")
    void shouldGetUserBuyLotteryDetailOK() throws Exception {

        String userId = "1234567890";
        List<String> lotteries = new ArrayList<>();
        lotteries.add("000001");
        lotteries.add("000002");

        Integer count = 2;
        Integer cost = 160;

        LotteryListDetailResponseDto response = new LotteryListDetailResponseDto(lotteries, count, cost);

        when(userService.getUserDetail(userId)).thenReturn(response);

        mockMvc.perform(get("/users/" + userId + "/lotteries/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(lotteries)))
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.cost", is(160)))
                .andReturn();
    }
    @Test
    @DisplayName("[getUserBuyLotteryDetail] Should be Bad Request (400) with userId is not number")
    void shouldBadRequestWithUserIdNotNumberAtGetUserBuyLotteryDetail() throws Exception {

        String userId = "OneTwoThree";

        mockMvc.perform(get("/users/" + userId + "/lotteries/"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[getUserBuyLotteryDetail] Should be Bad Request (400) with userId is less than 10 digits")
    void shouldBadRequestWithUserIdLessThanTenDigitsAtGetUserBuyLotteryDetail() throws Exception {

        String userId = "123456789";

        mockMvc.perform(get("/users/" + userId + "/lotteries/"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("[getUserBuyLotteryDetail] Should be Bad Request (400) with userId is more than 10 digits")
    void shouldBadRequestWithUserIdMoreThanTenDigitsAtGetUserBuyLotteryDetail() throws Exception {

        String userId = "12345678901";

        mockMvc.perform(get("/users/" + userId + "/lotteries/"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("[sellLottery] Should be delete user ticket and Status OK (200)")
    void shouldSellLotteryOK() throws Exception {

        String userId = "1234567890";
        String ticketId = "123456";

//        LotteryResponseDto response = new LotteryResponseDto(ticketId);

        when(userService.sellLotteryByUserIdAndTicketId(userId, ticketId)).thenReturn(ticketId);

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is(ticketId)))
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with userId is not number")
    void shouldBadRequestWithUserIdNotNumberAtSellLottery() throws Exception {

        String userId = "OneTwoThree";
        String ticketId = "123456";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with userId is less than 10 digits")
    void shouldBadRequestWithUserIdLessThanTenDigitsAtSellLottery() throws Exception {

        String userId = "123456789";
        String ticketId = "123456";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with userId is more than 10 digits")
    void shouldBadRequestWithUserIdMoreThanTenDigitsAtSellLottery() throws Exception {
        
        String userId = "12345678901";
        String ticketId = "123456";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with ticketId is not number")
    void shouldBadRequestWithTicketIdNotNumberAtSellLottery() throws Exception {

        String userId = "1234567890";
        String ticketId = "OneTwoThree";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with ticketId is less than 6 digits")
    void shouldBadRequestWithTicketIdLessThanSixDigitsAtSellLottery() throws Exception {

        String userId = "1234567890";
        String ticketId = "12345";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("[sellLottery] Should be Bad Request (400) with ticketId is more than 6 digits")
    void shouldBadRequestWithTicketIdMoreThanSixDigitsAtSellLottery() throws Exception {
        
        String userId = "1234567890";
        String ticketId = "1234567";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}