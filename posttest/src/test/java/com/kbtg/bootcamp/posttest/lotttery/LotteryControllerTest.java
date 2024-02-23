package com.kbtg.bootcamp.posttest.lotttery;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kbtg.bootcamp.posttest.exception.InternalServerErrorException;
import com.kbtg.bootcamp.posttest.lottery.controller.LotteryController;
import com.kbtg.bootcamp.posttest.lottery.response.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

  MockMvc mockMvc;

  @Mock
  private LotteryService lotteryService;

  @BeforeEach
  public void setUp() {
    LotteryController lotteryController = new LotteryController(lotteryService);
    mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
        .alwaysDo(print())
        .build();
  }

  @Test
  @DisplayName("when get /lotteries then return 200")
  void getAllTickets() throws Exception {
    mockMvc.perform(get("/lotteries"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("when get /lotteries then return data")
  void getAllTickets_Success() throws Exception {
    List<String> tickets = List.of("123456", "123457");

    when(lotteryService.getAllTickets()).thenReturn(new TicketResponse(tickets));

    mockMvc.perform(get("/lotteries"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("when get /lotteries then return 500")
  void getAllTickets_Fail() throws Exception {
    when(lotteryService.getAllTickets()).thenThrow(
        new InternalServerErrorException("Internal Server Error"));
    mockMvc.perform(get("/lotteries"))
        .andExpect(status().isInternalServerError());
  }
}