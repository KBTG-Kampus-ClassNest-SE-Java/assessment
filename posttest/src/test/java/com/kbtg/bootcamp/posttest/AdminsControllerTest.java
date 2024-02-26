package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.controller.AdminsController;
import com.kbtg.bootcamp.posttest.dto.AdminRequestDto;
import com.kbtg.bootcamp.posttest.dto.AdminResponseDto;
import com.kbtg.bootcamp.posttest.service.AdminsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminsControllerTest {

    MockMvc mockMvc;
    @Mock
    AdminsService adminsService;

    @BeforeEach
    void setUp() {
        AdminsController adminsController = new AdminsController(adminsService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminsController)
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("when creating a lottery ticket, should return status 201 and the created ticket")
    void createLottery() throws Exception {
        AdminRequestDto requestDto = new AdminRequestDto("111111", 80, 1);
        AdminResponseDto responseDto = new AdminResponseDto(requestDto.getTicket());

        given(adminsService.createLottery(any())).willReturn(responseDto);

        String requestJson = "{"
                + "\"ticket\":\"111111\","
                + "\"price\":80,"
                + "\"amount\":1"
                + "}";

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is("111111")));
    }

}
