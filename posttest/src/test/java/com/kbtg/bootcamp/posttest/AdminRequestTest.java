package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.user.AdminController;
import com.kbtg.bootcamp.posttest.user.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class AdminRequestTest {

    MockMvc mockMvc;
    @Mock
    AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(adminService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    @DisplayName("when create lottery perform on POST: /admin/lotteries should return status 200 and body return only ticket that valid value")
    @ParameterizedTest
    @CsvSource({
                "123456, 123456",
                "652341, 652341",
                "852963, 852963"
    })
    void createLottery(String ticketTestInput, String expectedResult) throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTicket(ticketTestInput);

        when(adminService.createLottery(any()))
                .thenReturn(lottery.getTicket());


        String keyJsonFormat = String.format("{\"ticket\":\"%s\"}", ticketTestInput);

        mockMvc.perform(
                post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(keyJsonFormat)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is(expectedResult)));
    }


}
