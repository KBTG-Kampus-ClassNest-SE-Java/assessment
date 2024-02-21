package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.exception.ResourceUnavailableException;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.service.UserService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("0000000001 purchases lottery ticket id 123456 should return id: 1 from userTicket")
    void testPurchaseLotteryTicket() throws Exception {
        String userId = "0000000001";
        String ticketId = "123456";

        UserTicketResponse expectedUserTicketResponse = new UserTicketResponse(1);

        when(userService.purchaseLotteryTicket(userId, ticketId)).thenReturn(expectedUserTicketResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUserTicketResponse.id()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("0000000001 purchases 2 lottery tickets id 123456 should return id: 1 and 2 from userTicket")
    void testPurchaseTwoOfTheSameLotteryTicket() throws Exception {
        String userId = "0000000001";
        String[] ticketIds = {"123456", "789012"};

        UserTicketResponse expectedUserTicketResponseOne = new UserTicketResponse(1);
        UserTicketResponse expectedUserTicketResponseTwo = new UserTicketResponse(2);

        when(userService.purchaseLotteryTicket(eq(userId), anyString())).thenAnswer(invocation -> {
            String ticketId = invocation.getArgument(1);
            if (ticketId.equals(ticketIds[0])) {
                return expectedUserTicketResponseOne;
            } else if (ticketId.equals(ticketIds[1])) {
                return expectedUserTicketResponseTwo;
            }
            return null;
        });

        for (String ticketId : ticketIds) {
            mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                            .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ticketId.equals("123456") ? 1 : 2));
        }
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Purchasing a lottery ticket encounters internal server error should return error message")
    void testPurchaseLotteryTicketButInternalServerError() throws Exception {
        String userId = "0000000001";
        String ticketId = "123456";

        when(userService.purchaseLotteryTicket(userId, ticketId)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An internal error occurred when adding a lottery ticket to user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("INTERNAL_SERVER_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Purchasing unavailable lottery ticket should return error message")
    void testPurchaseUnavailableLotteryTicket() throws Exception {
        String userId = "0000000001";
        String ticketId = "999999";

        when(userService.purchaseLotteryTicket(userId, ticketId))
                .thenThrow(new ResourceUnavailableException("ticketId: " + ticketId + " unavailable (not found or out of stock)"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ticketId: " + ticketId + " unavailable (not found or out of stock)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Purchasing a lottery ticket with non existing userId should return error message")
    void testPurchaseLotteryTicketWithNonExistingUserId() throws Exception {
        String userId = "1234567890";
        String ticketId = "999999";

        when(userService.purchaseLotteryTicket(userId, ticketId))
                .thenThrow(new ResourceUnavailableException("userId: " + userId + " not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                        .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("userId: " + userId + " not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890, 123",
            "123, 1234567890",
            "123, 123",
    })
    @WithMockUser(username = "user", roles = "USER")
    public void testPurchaseLotteryTicketWithInvalidData(String userId, String ticketId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
