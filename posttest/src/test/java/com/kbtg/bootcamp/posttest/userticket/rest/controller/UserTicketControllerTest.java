package com.kbtg.bootcamp.posttest.userticket.rest.controller;

import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserTicketController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserTicketControllerTest {

    private static final String TICKET = "123456";
    private static final String USER_ID = "0123456789";

    private static MockMvc mockMvc;

    @Autowired
    private UserTicketController userTicketController;

    @MockBean
    private UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController).build();
    }

    @Test
    @DisplayName("Should returns list of lottery when using getLotteryByUserId")
    void testGetLotteriesByUserId() throws Exception {

        UserTicketResDto userTicketResponseDto = new UserTicketResDto();
        userTicketResponseDto.setTickets(List.of("123456"));
        userTicketResponseDto.setTotalPrice(100);
        userTicketResponseDto.setCount(1);
        when(userTicketService.getLotteryByUserId(Mockito.any()))
                .thenReturn(userTicketResponseDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{userId}/lotteries", USER_ID);

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"tickets\":[\"123456\"],\"count\":1,\"totalPrice\":100}"));
    }


    @Test
    @DisplayName("Should returns ticket when successfully delete")
    void testDeleteLotteriesByUserId() throws Exception {

        when(userTicketService.sellLottery(Mockito.any(), Mockito.any()))
                .thenReturn(new LotteryResponseDto(TICKET));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{userId}/lotteries/{ticketId}",
                USER_ID, TICKET);

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"ticket\":\"123456\"}"));
    }
}






