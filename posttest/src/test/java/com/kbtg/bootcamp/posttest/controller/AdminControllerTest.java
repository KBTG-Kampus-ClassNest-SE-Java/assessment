package com.kbtg.bootcamp.posttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.request.CreateTicketRequestDTO;
import com.kbtg.bootcamp.posttest.dto.response.CreateTicketResponseDTO;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    public static final String TICKET_NUMBER = "123456";
    public static final int PRICE = 80;
    public static final int AMOUNT = 1;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(ticketService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testAddingLotteryWithInValidRequestBody() throws Exception {

        // Request body
        CreateTicketRequestDTO createTicketRequestDTO = new CreateTicketRequestDTO();
        createTicketRequestDTO.setTicket(TICKET_NUMBER);
        createTicketRequestDTO.setPrice(PRICE);
        createTicketRequestDTO.setAmount(AMOUNT);
        String request = objectMapper.writeValueAsString(createTicketRequestDTO);

        // Mocking ticketService
        when(ticketService.createTicket(createTicketRequestDTO)).thenReturn(new CreateTicketResponseDTO(TICKET_NUMBER));

        // Assert controller
        String expectedTicketNumber = "123456";
        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(jsonPath("$.ticket", is(expectedTicketNumber)))
                .andExpect(status().isCreated());
    }

}
