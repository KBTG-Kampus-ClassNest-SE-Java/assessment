package com.kbtg.bootcamp.posttest.userticket.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserTicketController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserTicketControllerTest {
    public static final String TICKET = "000001";
    public static final String USER_ID = "0881234567";
    public static final String USER_ID_INVALID = "abcdefghij";
    @Autowired
    private UserTicketController userTicketController;

    @MockBean
    private UserTicketService userTicketService;

    @Test
    @DisplayName("should return list lottery when get by userId")
    void testGetLotteriesByUserId() throws Exception {
        // Arrange
        UserTickerSummaryDto userTickerSummaryDto = new UserTickerSummaryDto();
        userTickerSummaryDto.setTickets(Arrays.asList("000001"));
        userTickerSummaryDto.setCost(80);
        userTickerSummaryDto.setCount(1);
        when(userTicketService.getLotteriesByUserId(Mockito.<String>any()))
                .thenReturn(userTickerSummaryDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{userId}/lotteries", USER_ID);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userTicketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"tickets\":[\"000001\"],\"count\":1,\"cost\":80}"));
    }

    @Test
    @DisplayName("should return ticket when delete success")
    void testDeleteLotteriesByUserId() throws Exception {
        // Arrange
        when(userTicketService.deleteLotteriesByUserId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new TicketResponseDto(TICKET));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{userId}/lotteries/{ticketId}",
                USER_ID, TICKET);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userTicketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"ticket\":\"000001\"}"));
    }
}
