package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(LotteryController.class)
class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("When add lottery ticket number 123456 should return ticket: 123456")
    public void testAddLotteryTicket() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 80, \"amount\": 1}";
        LotteryTicketResponse lotteryTicketResponse = new LotteryTicketResponse("123456");

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenReturn(lotteryTicketResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").value(lotteryTicketResponse.ticket()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("When add lottery ticket but encounter internal server error should return error message")
    public void testAddLotteryTicketButInternalServerError() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 80, \"amount\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An internal error occurred when creating a lottery ticket"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("INTERNAL_SERVER_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @ParameterizedTest
    @WithMockUser(username = "admin", roles = "ADMIN")
    @CsvSource({
            "A, 80, 1",
            "1, 80, 1",
            "1234567, 80, 1",
    })
    @DisplayName("When add invalid ticket number should return ticket number must be 6 digits")
    public void testAddLotteryTicketWithInvalidTicketValue(String ticketNumber, int price, int amount) throws Exception {
        String requestJson = String.format("{\"ticket\": \"%s\", \"price\": %d, \"amount\": %d}", ticketNumber, price, amount);

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").value("ticket number must be 6 digits"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("When add ticket with 0 price should return price should not be less than 1")
    public void testAddLotteryTicketWithZeroPriceShouldReturnError() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 0, \"amount\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("price should not be less than 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("When add ticket with 0 amount should return amount should not be less than 1")
    public void testAddLotteryTicketWithZeroAmountShouldReturnError() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 1, \"amount\": 0}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("amount should not be less than 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddLotteryTicketWithoutTicketValue() throws Exception {
        String requestJson = "{\"price\": 80, \"amount\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").value("ticket is required"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddLotteryTicketWithoutPriceValue() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"amount\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("price is required"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddLotteryTicketWithoutAmountValue() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("amount is required"));
    }

}
