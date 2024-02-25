package com.kbtg.bootcamp.posttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.controller.LotteryController;
import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    MockMvc mockMvc;
    @Mock
    private LotteryService lotteryService;
    @Mock
    private UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService,userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Test get lotteries")
    void testGetLotteries() throws Exception{
        List<String> expectedTicket = List.of("312311","312222","312222");
        when(lotteryService.getLottery()).thenReturn(expectedTicket);
        mockMvc.perform(get("/lotteries"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets", is(expectedTicket)))
                .andExpect(status().isOk());

    }

    @Test
    void testAddLotteries() throws Exception {
        // Prepare request data
        LotteryRequestDto requestDto = new LotteryRequestDto("123456",80,20);

        // Stub the service method
        when(lotteryService.addLottery(any(LotteryRequestDto.class))).thenReturn("123456");
        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                    "ticket_id":"123456",
                                    "price":80,
                                    "amount":20
                                }                                        
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket").value("123456"));
    }

    @Test
    void testBuyLotteries() throws Exception{
        when(userTicketService.buyLottery(anyString(),anyString())).thenReturn(1);
        mockMvc.perform(post("/users/123/lotteries/456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetLotteriesById() throws Exception{
        List<UserTicket> userTickets = new ArrayList<>();
        userTickets.add(new UserTicket("2","123456"));
        userTickets.add(new UserTicket("2","123455"));
        when(userTicketService.getLotteriesById(anyString())).thenReturn(userTickets);

        mockMvc.perform(get("/users/2/lotteries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").isArray())
                .andExpect(jsonPath("$.ticket",is(List.of("123456","123455"))))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.cost").value(160));

    }

    @Test
    void testSellLotteries() throws Exception{
        doNothing().when(userTicketService).sellLotteries(anyInt(),anyString());

        mockMvc.perform(delete("/users/123/lotteries/12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticket").value("12"));

    }
}
