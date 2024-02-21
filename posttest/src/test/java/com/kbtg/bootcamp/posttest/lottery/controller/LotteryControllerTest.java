package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.exception.DuplicationException;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketListResponse;
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

import java.util.List;
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
    @DisplayName("Adding lottery ticket number 123456 should return ticket: 123456")
    void testAddLotteryTicket() throws Exception {
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
    @DisplayName("Adding lottery ticket encounters internal server error should return error message")
    void testAddLotteryTicketButInternalServerError() throws Exception {
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
    @DisplayName("Adding an invalid ticket number should return 'Ticket number must be 6 digits'")
    void testAddLotteryTicketWithInvalidTicketValue(String ticketNumber, int price, int amount) throws Exception {
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
    @DisplayName("Adding a ticket with a price of 0 should return 'Price should not be less than 1'")
    void testAddLotteryTicketWithZeroPriceShouldReturnError() throws Exception {
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
    @DisplayName("Adding a ticket with an amount of 0 should return 'Amount should not be less than 1'")
    void testAddLotteryTicketWithZeroAmountShouldReturnError() throws Exception {
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
    @DisplayName("Adding lottery ticket without ticket value")
    void testAddLotteryTicketWithoutTicketValue() throws Exception {
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
    @DisplayName("Adding lottery ticket without price value")
    void testAddLotteryTicketWithoutPriceValue() throws Exception {
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
    @DisplayName("Adding lottery ticket without amount value")
    void testAddLotteryTicketWithoutAmountValue() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("amount is required"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Having 2 lottery tickets, get lotteries should return 2 lottery tickets")
    void testGetTwoLotteryTickets() throws Exception {
        LotteryTicketListResponse lotteryTicketListResponse = new LotteryTicketListResponse(List.of("123456", "000000"));

        when(lotteryService.getLotteryTicketList())
                .thenReturn(new LotteryTicketListResponse(List.of("123456", "000000")));

        mockMvc.perform(MockMvcRequestBuilders.get("/lotteries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets.length()").value(lotteryTicketListResponse.tickets().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets[0]").value(lotteryTicketListResponse.tickets().get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets[1]").value(lotteryTicketListResponse.tickets().get(1)));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Not having any lottery tickets, get lotteries should return empty")
    void testGetZeroTickets() throws Exception {
        when(lotteryService.getLotteryTicketList())
                .thenReturn(new LotteryTicketListResponse(List.of()));

        mockMvc.perform(MockMvcRequestBuilders.get("/lotteries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets.length()").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets").isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Adding a lottery ticket should return an error message on encountering an internal server error")
    void testGetAllLotteryTicketsButInternalServerError() throws Exception {

        when(lotteryService.getLotteryTicketList()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/lotteries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An internal error occurred when getting lottery ticket list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Adding a duplicated lottery ticket should return an error message")
    void testAddDuplicatedLotteryTicket() throws Exception {
        String requestJson = "{\"ticket\": \"123456\", \"price\": 80, \"amount\": 1}";

        when(lotteryService.createLotteryTicket(any(LotteryTicketRequest.class)))
                .thenThrow(new DuplicationException("ticketId: 123456 already existing"));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ticketId: 123456 already existing"));
    }

}
