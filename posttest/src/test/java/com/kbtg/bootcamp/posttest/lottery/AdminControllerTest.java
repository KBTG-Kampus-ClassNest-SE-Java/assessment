package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    MockMvc mockMvc;

    @Mock
    LotteryService lotteryService;

    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).alwaysDo(print()).build();
    }
    @Test
    @DisplayName("Should be Status CREATED (201) and created lottery")
    void shouldCreateLottery() throws Exception {

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto("123456", 80,1);
        String request = objectWriter.writeValueAsString(lotteryRequestDto);

        when(lotteryService.createLottery(any())).thenReturn("123456");


        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is("123456")))
                .andReturn();
    }
    @Test
    @DisplayName("Should be Bad request (400) with ticket id is not number")
    void shouldBadRequestWithTicketIdIsNotNumber() throws Exception {

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto("000ABC", 80, 1);
        String request = objectWriter.writeValueAsString(lotteryRequestDto);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad request (400) with Ticket Id is less than 6 digits")
    void shouldBadRequestWithTicketIdIsLessThanSixDigits() throws Exception {

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto("12345", 80, 1);
        String request = objectWriter.writeValueAsString(lotteryRequestDto);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad request (400) with Ticket Id is more than 6 digits")
    void shouldBadRequestWithTicketIdIsMoreThanSixDigits() throws Exception {

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto("1234567", 80, 1);
        String request = objectWriter.writeValueAsString(lotteryRequestDto);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad request (400) with price is not number")
    void shouldBadRequestWithPriceIsNotNumber() throws Exception {

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"ticket\": \"000000\", \"price\": \"eight zero\", \"amount\": 1}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad Request (400) with price less than 0")
    void shouldBadRequestWithPriceIsLessThanZero() throws Exception {

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"ticket\": \"000000\", \"price\": -1, \"amount\": 1}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad Request (400) with amount is not number")
    void shouldBadRequestWithAmountIsNotNumber() throws Exception {

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"ticket\": \"000000\", \"price\": 80, \"amount\": \"a\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Should be Bad Request (400) with amount is less than 0")
    void shouldBadRequestWithAmountIsLessThanZero() throws Exception {

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"ticket\": \"000000\", \"price\": 80, \"amount\": -1}"))
                .andExpect(status().isBadRequest());
    }
}
