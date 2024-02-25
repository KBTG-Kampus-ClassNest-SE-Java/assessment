package com.kbtg.bootcamp.posttest.admin.controller;

import com.kbtg.bootcamp.posttest.admin.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.admin.service.AdminService;
import com.kbtg.bootcamp.posttest.app.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class AdminControllerTest {

  MockMvc mockMvc;
  @Mock
  AdminService adminService;

  private static Validator validator;


  @BeforeEach
  void setUp() {
    AdminController adminController = new AdminController(adminService);
    mockMvc = MockMvcBuilders.standaloneSetup(adminController).alwaysDo(print()).build();

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  @DisplayName("when preform on POST /admin/lotteries case createLottery success should return 201 and ticket response")
  @Order(1)
  void createLottery() throws Exception{
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setPrice(80);
    createLotteryRequest.setAmount(1);

    TicketResponse ticketResponse = TicketResponse.builder()
        .ticket("123456")
        .build();

    when(adminService.createLottery(createLotteryRequest)).thenReturn(ticketResponse);
    mockMvc.perform(
            post("/admin/lotteries")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"ticket\":\"123456\",\"price\":80,\"amount\":1}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.ticket", is("123456")));
  }

  @Test
  @DisplayName("when preform on POST /admin/lotteries case lottery already exists ticket should return 400")
  @Order(2)
  void createLotteryDuplicateTicket() throws Exception{
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setPrice(80);
    createLotteryRequest.setAmount(1);

    when(adminService.createLottery(createLotteryRequest)).thenThrow(new BadRequestException("Lottery already exists"));

    mockMvc.perform(
            post("/admin/lotteries")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"ticket\":\"123456\",\"price\":80,\"amount\":1}"))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertInstanceOf(BadRequestException.class,
            result.getResolvedException()))
        .andExpect(result -> assertEquals("Lottery already exists", Objects.requireNonNull(
            result.getResolvedException()).getMessage()));
  }



  @Test
  @DisplayName("validate ticket is required on CrateLotteryRequest")
  @Order(3)
  void ticketIsRequired() {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setPrice(80);
    createLotteryRequest.setAmount(1);

    var violations = validator.validate(createLotteryRequest);

    assertThat(violations).hasSize(1);
    Iterator<ConstraintViolation<CreateLotteryRequest>> iterator = violations.iterator();

    assertThat(iterator.next().getMessage()).isEqualTo("ticket is required");

  }

  @Test
  @DisplayName("validate ticket must be 6-digit number on CreateLotteryRequest")
  @Order(4)
  void ticketMustBe6DigitNumber() {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("12345");
    createLotteryRequest.setPrice(80);
    createLotteryRequest.setAmount(1);

    var violations = validator.validate(createLotteryRequest);

    assertThat(violations).hasSize(1);
    Iterator<ConstraintViolation<CreateLotteryRequest>> iterator = violations.iterator();

    assertThat(iterator.next().getMessage()).isEqualTo("ticket must be 6 number");
  }


  @Test
  @DisplayName("validate price is required on CreateLotteryRequest")
  @Order(5)
  void priceIsRequired() {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setAmount(1);

    var violations = validator.validate(createLotteryRequest);

    assertThat(violations).hasSize(1);
    Iterator<ConstraintViolation<CreateLotteryRequest>> iterator = violations.iterator();

    assertThat(iterator.next().getMessage()).isEqualTo("price is required");
  }

  @Test
  @DisplayName("validate amount is required on CreateLotteryRequest")
  @Order(6)
  void amountIsRequired() {
    CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest();
    createLotteryRequest.setTicket("123456");
    createLotteryRequest.setPrice(80);

    var violations = validator.validate(createLotteryRequest);

    assertThat(violations).hasSize(1);
    Iterator<ConstraintViolation<CreateLotteryRequest>> iterator = violations.iterator();

    assertThat(iterator.next().getMessage()).isEqualTo("amount is required");
  }

}