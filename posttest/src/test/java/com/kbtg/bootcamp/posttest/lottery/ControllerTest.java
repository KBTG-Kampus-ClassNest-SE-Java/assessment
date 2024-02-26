package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.controllers.AdminController;
import com.kbtg.bootcamp.posttest.controllers.UsersController;
import com.kbtg.bootcamp.posttest.models.lottery.LotteryRequestDTO;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.services.AdminService;
import com.kbtg.bootcamp.posttest.services.UsersSerivce;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    MockMvc mockMvc;

    @Mock
    AdminService adminService;
    @Mock
    UsersSerivce usersSerivce;


    void setUpAdmin() {
        AdminController adminController = new AdminController(adminService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    void setUpUser() {
        UsersController usersController = new UsersController(usersSerivce);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when create lottery on POST: /admin/lotteries should return status 201 and body contains ticket")
    void createLottery() throws Exception {

        setUpAdmin();

        TicketIdResponseDTO ticketIdResponseDTO = new TicketIdResponseDTO("123456");
        when(adminService.addLotteries(argThat(actual -> actual.getTicket().equals("123456"))))
                .thenReturn(ticketIdResponseDTO);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                   {
                                   "ticket": "123456",
                                   "price": 80,
                                   "amount": 1
                                   }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value("123456"))
                .andReturn();

        verify(adminService, times(1)).addLotteries(any());
    }

    @Test
    @DisplayName("when create lottery on POST: /admin/lotteries with wrong ticket format should return 400 bad request")
    void createLotteryWrongCredentials() throws Exception {

        setUpAdmin();

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                   {
                                   "ticket": "123456XXX",
                                   "price": 80,
                                   "amount": 1
                                   }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();
    }

    @Test
    @DisplayName("when create lottery on POST: /users/{userId}/lotteries/{ticketId} should return 200")
    void buyTicket() throws Exception {

        setUpUser();

        mockMvc.perform(post("/users/1234567890/lotteries/123456"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @DisplayName("when create lottery on POST: /users/{userId}/lotteries/{ticketId} with wrong ID should return 400")
    void buyTicketWrongId() throws Exception {

        setUpUser();
        mockMvc.perform(post("/users/1234567890xxxx/lotteries/123456xxxxx"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HandlerMethodValidationException))
                .andReturn();
    }

    @Test
    @DisplayName("Lottery ticket, price, amount invalid should return 3 violations")
    void lotteryTicketPriceAmountInvalid() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        LotteryRequestDTO request = new LotteryRequestDTO("12345x", 0.0, 0);

        var violations = validator.validate(request);

        assertThat(violations).hasSize(3);
        List<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertTrue(violationMessages.contains("Price must be positive"));
        assertTrue(violationMessages.contains("Amount must be positive"));
        assertTrue(violationMessages.contains("Ticket must be a 6 digits number"));

    }
}
