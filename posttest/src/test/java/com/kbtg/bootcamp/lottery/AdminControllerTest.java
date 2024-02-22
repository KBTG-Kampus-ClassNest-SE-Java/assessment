package com.kbtg.bootcamp.lottery;


import com.kbtg.bootcamp.lottery.controller.AdminController;
import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    MockMvc mockMvc;
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
    @DisplayName("when admin create lottery on POST: /admin/lotteries should return status 201 and body contain lottery number.")
    void createLottery() throws Exception {
        LotteryResponseDto responseDto = new LotteryResponseDto("123456");
        when(lotteryService.createLottery(any(LotteryRequestDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                """
                                {
                                	"ticket": "123456",
                                	"price": 80,
                                	"amount": 20
                                }
                                """
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is("123456")));

    }

}
