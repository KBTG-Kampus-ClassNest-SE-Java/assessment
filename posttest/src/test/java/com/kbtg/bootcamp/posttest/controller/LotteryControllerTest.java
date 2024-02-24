package com.kbtg.bootcamp.posttest.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;


    @BeforeEach
    void setup(){

        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

        @Test
        @DisplayName("when perform on GET: /lotteries should return lotterytNumber ")
        void getAllLotteries() throws Exception {
            List<Lottery> mockLotteries = List.of(new Lottery("123456", 80, 1),
                                                  new Lottery("456789", 80, 1));

            List<String> lottery = mockLotteries.stream().map(Lottery::getLotteryNumber).collect(Collectors.toList());;

            LotteryResponseDto lotteryResponseDto = new LotteryResponseDto(lottery);
            ResponseEntity<LotteryResponseDto> responseEntity = ResponseEntity.ok(lotteryResponseDto);

            when(lotteryService.getAllLotteries()).thenReturn(responseEntity);

            mockMvc.perform(get("/lotteries"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tickets", contains("123456", "456789")));
        }


    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} should return status 200 and contain ticket id.")
    void buyLottery() throws Exception {
        UserTicketResponseDto userTicketResponseDto = new UserTicketResponseDto(1);
        ResponseEntity<UserTicketResponseDto> responseEntity = ResponseEntity.ok(userTicketResponseDto);
        when(lotteryService.buyLottery(1, "123456"))
                .thenReturn(responseEntity);
        mockMvc.perform(post("/users/1/lotteries/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("when perform on GET: /users/{userId}/lotteries should return status 200 and return list of lotteries of user")
    void findLotteryByUserId() throws Exception {
      List<String> ticketList = List.of("123456", "456789");
      int total = 160;
      int count = 2;

      TicketResponseDto ticketResponseDto = new TicketResponseDto(ticketList, count, total);
      ResponseEntity<TicketResponseDto> responseEntity = ResponseEntity.ok(ticketResponseDto);
      when(lotteryService.findLotteryByUserId(1)).thenReturn(responseEntity);

      mockMvc.perform(get("/users/1/lotteries"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.tickets", is(ticketList)))
              .andExpect(jsonPath("$.count", is(2)))
              .andExpect(jsonPath("$.cost", is(160)));
    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} should return status 200 and return ticketNumber")
    void deleteTicketByUserId() throws Exception {
        Integer userId = 1;
        List<String> tickets = List.of("123456");
        LotteryResponseDto lotteryResponseDto = new LotteryResponseDto(tickets);
        ResponseEntity<LotteryResponseDto> responseEntity = ResponseEntity.ok(lotteryResponseDto);

        when(lotteryService.deleteTicketByUserId(1, "123456")).thenReturn(responseEntity);

        mockMvc.perform(delete("/users/1/lotteries/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets",contains("123456")));
    }


}