package com.kbtg.bootcamp.posttest.admin;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.admin.controller.AdminController;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

  MockMvc mockMvc;

  @Mock
  private LotteryService lotteryService;

  @BeforeEach
  public void setUp() {
    AdminController adminController = new AdminController(lotteryService);
    mockMvc = MockMvcBuilders.standaloneSetup(adminController)
        .alwaysDo(print())
        .build();
  }

  @Test
  @DisplayName("when post /admin/lotteries with valid request body then return 201")
  void createLottery_Success() throws Exception {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setAmount(1);
    createLotteryRequest.setPrice(80);

    when(lotteryService.createLottery(createLotteryRequest)).thenReturn("123456");

    mockMvc.perform(post("/admin/lotteries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createLotteryRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("when post /admin/lotteries with invalid request body then return 400")
  void createLottery_Fail() throws Exception {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("1234567");
    createLotteryRequest.setAmount(1);

    mockMvc.perform(post("/admin/lotteries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createLotteryRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("when post /admin/lotteries with existing ticket then return 409")
  void createLottery_Exist() throws Exception {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setAmount(1);
    createLotteryRequest.setPrice(80);

    when(lotteryService.checkExistTicket(createLotteryRequest.getTicket())).thenReturn(true);

    mockMvc.perform(post("/admin/lotteries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createLotteryRequest)))
        .andExpect(status().isConflict());
  }

//  @Test
//  @DisplayName("when post /admin/lotteries with exception then return 500")
//  void createLottery_Exception() throws Exception {
//    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
//    createLotteryRequest.setTicket("123456789");
//    createLotteryRequest.setAmount(1);
//    createLotteryRequest.setPrice(80);
//
//    when(lotteryService.createLottery(createLotteryRequest)).thenThrow(
//        new Exception("Internal Server Error")
//    );
//
//    mockMvc.perform(post("/admin/lotteries")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(createLotteryRequest)))
//        .andExpect(status().isInternalServerError());
//  }
}
