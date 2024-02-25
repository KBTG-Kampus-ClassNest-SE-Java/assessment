package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.response.ListAllTicketsResponseDTO;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    MockMvc mockMvc;
    @Mock
    private TicketService ticketService;

    private final List<String> lotteries = new ArrayList<>(Arrays.asList("000000","111111","112112"));

    @BeforeEach
    void setUp() {
        TicketController ticketController = new TicketController(ticketService);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testListAllLotteries() throws Exception {

        when(ticketService.listAllTickets()).thenReturn(new ListAllTicketsResponseDTO(lotteries));

        List<String> expectedLotteries = new ArrayList<>(Arrays.asList("000000","111111","112112"));
        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.tickets", is(expectedLotteries)))
                .andExpect(status().isOk());
    }

}
