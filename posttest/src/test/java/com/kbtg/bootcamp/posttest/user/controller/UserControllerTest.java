package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.kbtg.bootcamp.posttest.lottery.responese.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.user.dto.TicketIdResponse;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import java.util.List;
import org.hamcrest.core.Is;
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
class UserControllerTest {

  MockMvc mockMvc;
  @Mock
  UserService userService;

  @BeforeEach
  void setUp() {
    UserController userController = new UserController(userService);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .alwaysDo(print())
        .build();
  }

  @Test
  @DisplayName(" when perform Post /users/:userId/lotteries/:ticketId, should be returnee 200 and return ticket")
  void buyLottery() throws Exception {
    String ticketId = "123456";
    String userId = "0000000001";

    TicketIdResponse ticketResponse = TicketIdResponse.builder()
        .id("1")
        .build();
    when(userService.buyLottery(ticketId, userId)).thenReturn(ticketResponse);

    mockMvc.perform(post("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Is.is("Buy lottery success.")))
        .andExpect(jsonPath("$.data.id", Is.is("1")));
  }

  @Test
  @DisplayName("when perform delete /users/:userId/lotteries/:ticketId, should be returnee 200 and return ticket")
  void sellLottery() throws Exception {
    String ticket = "123456";
    String userId = "0000000001";

    TicketResponse ticketResponse = TicketResponse.builder()
        .ticket("123456")
        .build();
    when(userService.sellLottery(ticket, userId)).thenReturn(ticketResponse);

    mockMvc.perform(delete("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Is.is("Sell lottery success.")))
        .andExpect(jsonPath("$.data.ticket", Is.is("123456")));
  }

  @Test
  @DisplayName("when perform Get /users/:userId/lotteries, should be returnee 200 and return ticket summary")
  void getLotteriesByUserId() throws Exception {
    String userId = "0000000001";

    UserLotteryResponse userLotteryResponse = UserLotteryResponse.builder()
        .tickets(List.of("123456"))
        .count(1)
        .cost(80)
        .build();
    when(userService.getLotteriesByUserId(userId)).thenReturn(userLotteryResponse);


    mockMvc.perform(get("/users/0000000001/lotteries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Is.is("Get lotteries success.")))
        .andExpect(jsonPath("$.data.tickets[0]", Is.is("123456")))
        .andExpect(jsonPath("$.data.count", Is.is(1)))
        .andExpect(jsonPath("$.data.cost", Is.is(80)));
  }
}