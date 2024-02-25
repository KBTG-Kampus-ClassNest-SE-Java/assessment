package com.kbtg.bootcamp.posttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserTicketResponseDto;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setup() {
        AdminController adminController = new AdminController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("when perform on POST: /admin/lotteries should return status 201 and and ticket number.")
    void createLottery() throws Exception {
        LotteryResponseDto lotteryResponseDto = new LotteryResponseDto(Collections.singletonList("123456"));
        ResponseEntity<LotteryResponseDto> responseEntity = ResponseEntity.status(201).body(lotteryResponseDto);
        when(lotteryService.createLottery(any(LotteryRequestDto.class))).thenReturn(responseEntity);
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                """
                                        {
                                        	"tickets": "123456",
                                        	"price": 80,
                                        	"amount": 1
                                        }
                                        """
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tickets[0]", is("123456")));

    }
}