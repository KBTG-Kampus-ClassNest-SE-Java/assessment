package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.app.exception.IllegalArgumentException;
import com.kbtg.bootcamp.posttest.app.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import static org.hamcrest.core.Is.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.BDDMockito.willThrow;
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
import java.util.Objects;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest

@TestMethodOrder(OrderAnnotation.class)
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
  @DisplayName(" when preform on POST /users/:userId/lotteries/:ticketId should be returnee 200 and return ticket")
  @Order(1)
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
        .andExpect(jsonPath("$.message", is("Buy lottery success.")))
        .andExpect(jsonPath("$.data.id", Is.is("1")));
  }

  @Test
  @DisplayName("when preform on POST /users/:userId/lotteries/:ticketId with invalid ticketId should return 400")
  @Order(2)
  void buyLotteryWithInvalidTicketId() throws Exception {
    String ticket = "12345";
    String userId = "0000000001";

    willThrow(new IllegalArgumentException("ticket must be 6 number")).given(userService).buyLottery(ticket, userId);

    mockMvc.perform(post("/users/0000000001/lotteries/12345")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("ticket must be 6 number", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on POST /users/:userId/lotteries/:ticketId with invalid userId should return 400")
  @Order(3)
  void buyLotteryWithInvalidUserId() throws Exception {
    String ticket = "123456";
    String userId = "000000000";

    willThrow(new IllegalArgumentException("User ID must be a 10-digit number.")).given(userService).buyLottery(ticket, userId);

    mockMvc.perform(post("/users/000000000/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("User ID must be a 10-digit number.", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on POST /users/:userId/lotteries/:ticketId with lottery not found case should return 400")
  @Order(4)
  void buyLotteryWithLotteryNotFound() throws Exception {
    String ticket = "123456";
    String userId = "0000000001";

    willThrow(new NotFoundException("Lottery not found")).given(userService).buyLottery(ticket, userId);

    mockMvc.perform(post("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertInstanceOf(NotFoundException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("Lottery not found", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on POST /users/:userId/lotteries/:ticketId with lottery sold out case should return 400")
  @Order(5)
  void buyLotteryWithLotterySoldOut() throws Exception {
    String ticket = "123456";
    String userId = "0000000001";

    willThrow(new IllegalArgumentException("Lottery is sold out")).given(userService).buyLottery(ticket, userId);

    mockMvc.perform(post("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("Lottery is sold out", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }



  @Test
  @DisplayName("when preform on GET /users/:userId/lotteries should be returnee 200 and return ticket summary")
  @Order(6)
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
  @Test
  @DisplayName("when preform on GET /users/:userId/lotteries with invalid userId should return 400")
  @Order(7)
  void getLotteriesByUserIdWithInvalidUserId() throws Exception {
    String userId = "000000000";

    willThrow(new IllegalArgumentException("User ID must be a 10-digit number.")).given(userService).getLotteriesByUserId(userId);

    mockMvc.perform(get("/users/000000000/lotteries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("User ID must be a 10-digit number.", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on DELETE /users/:userId/lotteries/:ticketId should be returnee 200 and return ticket")
  @Order(8)
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
  @DisplayName("when preform on DELETE /users/:userId/lotteries/:ticketId with invalid ticketId should return 400")
  @Order(9)
  void deleteLotteryWithInvalidTicketId() throws Exception {
    String ticket = "12345";
    String userId = "0000000001";

    willThrow(new IllegalArgumentException("ticket must be 6 number")).given(userService).sellLottery(ticket, userId);

    mockMvc.perform(delete("/users/0000000001/lotteries/12345")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("ticket must be 6 number", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on DELETE /users/:userId/lotteries/:ticketId with invalid userId should return 400")
  @Order(10)
  void deleteLotteryWithInvalidUserId() throws Exception {
    String ticket = "123456";
    String userId = "000000000";

    willThrow(new IllegalArgumentException("User ID must be a 10-digit number.")).given(userService).sellLottery(ticket, userId);

    mockMvc.perform(delete("/users/000000000/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("User ID must be a 10-digit number.", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on DELETE /users/:userId/lotteries/:ticketId with lottery not found case should return 404")
  @Order(11)
  void deleteLotteryWithLotteryNotFound() throws Exception {
    String ticket = "123456";
    String userId = "0000000001";

    willThrow(new NotFoundException("Lottery not found")).given(userService).sellLottery(ticket, userId);

    mockMvc.perform(delete("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertInstanceOf(NotFoundException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("Lottery not found", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }

  @Test
  @DisplayName("when preform on DELETE /users/:userId/lotteries/:ticketId with user not bought this lottery case should return 400")
  @Order(12)
  void deleteLotteryWithUserNotBoughtThisLottery() throws Exception {
    String ticket = "123456";
    String userId = "0000000001";

    willThrow(new IllegalArgumentException("User did not buy this lottery")).given(userService).sellLottery(ticket, userId);

    mockMvc.perform(delete("/users/0000000001/lotteries/123456")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(IllegalArgumentException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("User did not buy this lottery", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }
}