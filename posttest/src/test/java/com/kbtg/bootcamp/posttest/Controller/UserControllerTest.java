package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.Service.UserTicketService;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId should return string id and status isCreated")
    public void TestBuyTicketSuccess() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userTicketService.BuyTicket(userId, ticketId)).thenReturn("1");

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with non 10-digits userId should throw BadRequestException")
    public void TestBuyTicketFailUserId() throws Exception {
        String userId = "12345";
        String ticketId = "123456";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with non numeric userId should throw BadRequestException")
    public void TestBuyTicketFailNonNumericUserId() throws Exception {
        String userId = "abcdefghik";
        String ticketId = "12345";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with mixed non numeric userId should throw BadRequestException")
    public void TestBuyTicketFailMixedNonNumericUserId() throws Exception {
        String userId = "123456789a";
        String ticketId = "12345";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with non 6-digits ticketId should throw BadRequestException")
    public void TestBuyTicketFailTicketId() throws Exception {
        String userId = "1234567890";
        String ticketId = "12345";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with non numeric ticketId should throw BadRequestException")
    public void TestBuyTicketFailNonNumericTicketId() throws Exception {
        String userId = "1234567890";
        String ticketId = "abcdef";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("POST:/users/:userId/lotteries/:ticketId with mixed non numeric ticketId should throw BadRequestException")
    public void TestBuyTicketFailMixedNonNumericTicketId() throws Exception {
        String userId = "1234567890";
        String ticketId = "12345a";

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).BuyTicket(any(), any());
    }

    @Test
    @DisplayName("GET:/users/:userId/lotteries get One should return status ok")
    public void TestGetOneLottery() throws Exception {
        String ticketId = "123456";
        String userId = "1234567890";

        UserTicketResponseDto userTicket = new UserTicketResponseDto(List.of(ticketId), 10L, 100.0);
        when(userTicketService.getUserTicket(userId)).thenReturn(userTicket);

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets", hasSize(1)))
                .andExpect(jsonPath("$.tickets[0]").value(ticketId))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.cost").value(100.0));

        verify(userTicketService, times(1)).getUserTicket(userId);
    }

    @Test
    @DisplayName("GET:/users/:userId/lotteries get Many should return status ok")
    public void TestGetManyLotteries() throws Exception {
        String ticketId = "123456";
        String ticketId2 = "654321";
        String userId = "1234567890";

        UserTicketResponseDto userTicket = new UserTicketResponseDto(List.of(ticketId, ticketId2), 10L, 100.0);
        when(userTicketService.getUserTicket(userId)).thenReturn(userTicket);

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets", hasSize(2)))
                .andExpect(jsonPath("$.tickets[0]").value(ticketId))
                .andExpect(jsonPath("$.tickets[1]").value(ticketId2))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.cost").value(100.0));

        verify(userTicketService, times(1)).getUserTicket(userId);
    }

    @Test
    @DisplayName("GET:/users/:userId/lotteries with non 6 digits userId should return status badRequest")
    public void TestGetLotteriesFailNon6DigitsUserId() throws Exception {
        String userId = "12345";

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).getUserTicket(any());
    }

    @Test
    @DisplayName("GET:/users/:userId/lotteries with non numeric userId should return status badRequest")
    public void TestGetLotteriesFailNonNumericUserId() throws Exception {
        String userId = "abcdefghik";

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).getUserTicket(any());
    }

    @Test
    @DisplayName("GET:/users/:userId/lotteries with mixed non numeric userId should return status badRequest")
    public void TestGetLotteriesFailMixedNonNumericUserId() throws Exception {
        String userId = "12345a";

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).getUserTicket(any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId should return status ok")
    public void TestDeleteLottery() throws Exception {
        String ticketId = "123456";
        String userId = "1234567890";

        doNothing().when(userTicketService).deleteLottery(userId, ticketId);

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").isString())
                .andExpect(jsonPath("$.ticket").value(ticketId));

        verify(userTicketService, times(1)).deleteLottery(userId, ticketId);
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with non 6 digits UserId should return status badRequest")
    public void TestDeleteLotteryFailNon6DigitsUserId() throws Exception {
        String ticketId = "123456";
        String userId = "12345";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with non numeric UserId should return status badRequest")
    public void TestDeleteLotteryFailNonNumericUserId() throws Exception {
        String ticketId = "123456";
        String userId = "abcdefghik";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with mixed non numeric UserId should return status badRequest")
    public void TestDeleteLotteryFailMixedNonNumericUserId() throws Exception {
        String ticketId = "123456";
        String userId = "12345a";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with non 6 digits ticketId should return status badRequest")
    public void TestDeleteLotteryFailNon6DigitsTicketId() throws Exception {
        String ticketId = "12345";
        String userId = "1234567890";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with non numeric ticketId should return status badRequest")
    public void TestDeleteLotteryFailNonNumericTicketId() throws Exception {
        String ticketId = "abcdefghik";
        String userId = "1234567890";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }

    @Test
    @DisplayName("DELETE:/users/:userId/lotteries/:ticketId with mixed non numeric ticketId should return status badRequest")
    public void TestDeleteLotteryFailMixedNonNumericTicketId() throws Exception {
        String ticketId = "12345a";
        String userId = "1234567890";

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isBadRequest());

        verify(userTicketService, never()).deleteLottery(any(), any());
    }
}