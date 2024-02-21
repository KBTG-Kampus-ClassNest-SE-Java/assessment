package com.kbtg.bootcamp.posttest.admin.controller;

import com.kbtg.bootcamp.posttest.admin.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.admin.service.AdminService;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.assertj.core.api.Assertions.assertThat;
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
  @DisplayName("when create lottery success with CreateLotteryRequest on POST:/admin/lotteries should return 201 and ticket response")
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
  @DisplayName("validate ticket is required on CrateLotteryRequest")
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