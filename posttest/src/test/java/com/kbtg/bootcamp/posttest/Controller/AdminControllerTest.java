package com.kbtg.bootcamp.posttest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.Service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    private MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("POST:/admin/lotteries should create new lottery and return status isCreated")
    public void TestCreateLottery() throws Exception {
        String ticketId = "123456";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, 100.0);

        when(lotteryService.createLottery(lotteryRequestDto)).thenReturn(ticketId);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lotteryRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").isString())
                .andExpect(jsonPath("$.ticket").value(ticketId));

        verify(lotteryService, times(1)).createLottery(eq(lotteryRequestDto));
    }

    @Test
    @DisplayName("POST:/admin/lotteries with null ticketId should return status badRequest")
    public void TestCreateLotteryFailNonBlank() throws Exception {
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(null, 10L, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with non 6 digits should return status badRequest")
    public void TestCreateLotteryFailNon6Digits() throws Exception {
        String ticketId = "12345";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with non numeric ticketId should return status badRequest")
    public void TestCreateLotteryFailNonNumeric() throws Exception {
        String ticketId = "abc";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with mixed non numeric ticketId should return status badRequest")
    public void TestCreateLotteryFailMixedNonNumeric() throws Exception {
        String ticketId = "abc123";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with null amount should return status badRequest")
    public void TestCreateLotteryFailNullAmount() throws Exception {
        String ticketId = "123456";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, null, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with negative amount should return status badRequest")
    public void TestCreateLotteryFailNegativeAmount() throws Exception {
        String ticketId = "123456";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, -10L, 100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with null price should return status badRequest")
    public void TestCreateLotteryFailNullPrice() throws Exception {
        String ticketId = "123456";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, null);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST:/admin/lotteries with negative price should return status badRequest")
    public void TestCreateLotteryFailNegativePrice() throws Exception {
        String ticketId = "123456";
        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticketId, 10L, -100.0);

        mockMvc.perform(post("/admin/lotteries"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE:/admin/lotteries/{Id} delete ID should success")
    public void TestDeleteLotterySuccess() throws Exception {
        String ticketId = "123456";
        String Id = "1";

        when(lotteryService.deleteLottery(Id)).thenReturn(ticketId);

        mockMvc.perform(delete("/admin/lotteries/" + Id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").isString())
                .andExpect(jsonPath("$.ticket").value(ticketId));

        verify(lotteryService, times(1)).deleteLottery(Id);
    }
}