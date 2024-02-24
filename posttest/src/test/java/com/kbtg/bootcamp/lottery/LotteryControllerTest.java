package com.kbtg.bootcamp.lottery;


import com.kbtg.bootcamp.lottery.controller.LotteryController;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on GET: /lotteries should return status 200 and body contain list of lotteries.")
    void viewLotteries() throws Exception {
        List<String> ticketNumbers = List.of("123456", "123457");
        LotteryResponseDto Lotteries = new LotteryResponseDto(ticketNumbers);
        when(lotteryService.getAllLotteryTickets()).thenReturn(Lotteries);

        mockMvc.perform(
                        get("/lotteries")
                )
                .andExpect(jsonPath("$.tickets", is(ticketNumbers)))
                .andExpect(status().isOk());
    }

}
