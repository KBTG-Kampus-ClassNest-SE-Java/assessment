package com.kbtg.bootcamp.posttest.lottery.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryListResDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LotteryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class LotteryControllerTest {
    @MockBean
    private LotteryServiceImp lotteryServiceImp;

    @Autowired
    private LotteryController lotteryController;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockMvc mockMvc;

    private static final String TICKET = "123456";



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController).build();
    }


    @Test
    @DisplayName("should return 201 and ticket when create success")
    void testCreate_Returns201AndTicket_WhenCreationSuccess() throws Exception {
        when(lotteryServiceImp.createLottery(Mockito.any(LotteryRequestDto.class)))
                .thenReturn(new LotteryResponseDto(TICKET));

        LotteryRequestDto requestDto = new LotteryRequestDto(); // Populate with data
        String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"ticket\":\"" + TICKET + "\"}"));
    }


    @Test
    @DisplayName("should return null when no lottery")
    void testRead() throws Exception {

        when(lotteryServiceImp.listAllLotteries()).thenReturn(new LotteryListResDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/lotteries")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"tickets\":null}"));
    }

}
