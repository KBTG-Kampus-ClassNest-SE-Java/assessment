package com.kbtg.bootcamp.posttest.lottery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.service.LoterryService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LotteryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LotteryControllerTest {
    public static final String TICKET = "000001";
    @MockBean
    private LoterryService loterryService;

    @Autowired
    private LotteryController lotteryController;

    @Test
    @DisplayName("should return 201 and ticket when create success")
    void testCreate() throws Exception {
        // Arrange
        when(loterryService.save(Mockito.<LotteryRequestDto>any())).thenReturn(new TicketResponseDto(TICKET));

        Lottery lottery = new Lottery();
        lottery.setAmount(1);
        lottery.setPrice(80);
        lottery.setTicket(TICKET);
        String content = (new ObjectMapper()).writeValueAsString(lottery);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/lotteries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(lotteryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"ticket\":\"" + TICKET + "\"}"));
    }

    @Test
    @DisplayName("should return null when no lottery")
    void testRead() throws Exception {
        // Arrange
        when(loterryService.findAll()).thenReturn(new TicketListResponseDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/lotteries");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(lotteryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"tickets\":null}"));
    }
}
