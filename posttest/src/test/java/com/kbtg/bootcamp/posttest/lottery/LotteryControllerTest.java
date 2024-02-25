package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.userTicket.UserTicketResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LotteryControllerTest {

    @Autowired
    private LotteryController lotteryController;

    @Mock
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(lotteryController).build();
    }

}