package com.kbtg.bootcamp.posttest.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.response.GetAllTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import com.kbtg.bootcamp.posttest.user.controller.UserController;
import com.kbtg.bootcamp.posttest.user.service.UserService;
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
public class UserControllerTest {

  MockMvc mockMvc;

  @Mock
  private LotteryService lotteryService;

  @Mock
  private UserService userService;

  @BeforeEach
  public void setUp() {
    UserController userController = new UserController(userService, lotteryService);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .alwaysDo(print())
        .build();
  }

  @Test
  @DisplayName("when buy ticket by user id then return 200")
  void buyTicketByUserId() throws Exception {
    when(lotteryService.checkAmountLottery("123456")).thenReturn(true);
    when(userService.buyTicketByUserId(1L, "123456")).thenReturn(1L);

    mockMvc.perform(post("/users/1/lotteries/123456"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("when buy ticket out of stock by user id then return 400")
  void buyTicketByUserId_Fail() throws Exception {
    String ticket = "123456";

    when(lotteryService.checkAmountLottery(ticket)).thenReturn(false);

    mockMvc.perform(post("/users/1/lotteries/" + ticket)
            .contentType("application/json")
            .content("ticket" + ticket + " is out of stock"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("when get ticket by user id then return 200")
  void getTicketByUser() throws Exception {
    List<Lottery> lotteries = List.of(
        new Lottery(1L, "123123", 1, 80),
        new Lottery(2L, "234234", 1, 80)
    );
    GetAllTicketResponse ticketResponse = new GetAllTicketResponse();
    List<String> tickets = lotteries.stream().map(lottery -> lottery.getTicket()).toList();
    int cost = lotteries.stream().map(Lottery::getPrice).reduce(0, Integer::sum);
    int amount = lotteries.size();
    ticketResponse.setTickets(tickets);
    ticketResponse.setCost(cost);
    ticketResponse.setCount(amount);
    when(userService.getAllTicketsByUserId(1L)).thenReturn(ticketResponse);

    mockMvc.perform(get("/users/1/lotteries"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("when get ticket by user id then return 404")
  void getTicketByUser_Fail() throws Exception {
    when(userService.getAllTicketsByUserId(1L)).thenThrow(
        new NotFoundException("User not found with id: 1"));

    mockMvc.perform(get("/users/1/lotteries"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("when delete ticket by user id then return 200")
  void deleteUserTicket() throws Exception {
    when(userService.deleteUserTicket(1L, "123456")).thenReturn("123456");

    mockMvc.perform(delete("/users/1/lotteries/123456"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("when delete ticket by user id then return 404")
  void deleteUserTicket_Fail() throws Exception {
    when(userService.deleteUserTicket(1L, "123456")).thenThrow(
        new NotFoundException("Ticket not found with ticket: 123456"));

    mockMvc.perform(delete("/users/1/lotteries/123456"))
        .andExpect(status().isNotFound());
  }
}
