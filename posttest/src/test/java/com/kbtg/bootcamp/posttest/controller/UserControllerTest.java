package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.RefundTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.UserPurchaseHistoryResponseDTO;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    public static final String USER_ID = "1234567890";
    public static final String TICKET_ID = "111111";
    public static final int TRANSACTION_ID = 1;

    private static final Set<String> PURCHASED_TICKET = Set.of("111111","112112","000000");
    public static final int TICKET_AMOUNT = 3;
    public static final int TOTAL_TICKET_COST = 240;
    private MockMvc mockMvc;

    @Mock
    private UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        UserTicketController userTicketController = new UserTicketController(userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testPurchaseLottery() throws Exception {

        when(userTicketService.purchaseTicket(USER_ID, TICKET_ID)).thenReturn(new PurchaseTicketResponseDTO(TRANSACTION_ID));


        int expectedTransactionId = 1;
        mockMvc.perform(
                        post(String.format("/users/%s/lotteries/%s", USER_ID, TICKET_ID))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id", is(expectedTransactionId)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testShowHistoryPurchase() throws Exception{

        when(userTicketService.showHistoryPurchase(USER_ID)).thenReturn(new UserPurchaseHistoryResponseDTO(
                PURCHASED_TICKET,
                TICKET_AMOUNT,
                TOTAL_TICKET_COST
        ));

        Set<String> expectedPurchasedTicket = Set.of("111111","112112","000000");
        int expectedTicketAmount = 3;
        int expectedTotalTicketCost = 240;
        mockMvc.perform(get(String.format("/users/%s/lotteries", USER_ID)))
                .andExpect(jsonPath("$.tickets",containsInAnyOrder(expectedPurchasedTicket.toArray())))
                .andExpect(jsonPath("$.count",is(expectedTicketAmount)))
                .andExpect(jsonPath("$.cost",is(expectedTotalTicketCost)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRefundTicket() throws Exception {
        when(userTicketService.refundTicket(USER_ID,TICKET_ID)).thenReturn(new RefundTicketResponseDTO(TICKET_ID));

        String expectedTicket = "111111";
        mockMvc.perform(delete(String.format("/users/%s/lotteries/%s", USER_ID,TICKET_ID)))
                .andExpect(jsonPath("$.ticket",is(expectedTicket)))
                .andExpect(status().isOk());
    }
}
