package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.configs.SecurityConfig;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Given user is anonymous when create lottery then response with status unauthorized")
    void givenUserIsAnonymous_whenCreateLottery_thenResponseWithStatusUnauthorized() throws Exception {
        this.mvc.perform(
                post("/admin/lotteries")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "notadmin", roles = "USER")
    void givenUserIsNotAdmin_whenCreateLottery_thenResponseWithStatusForbidden() throws Exception {
        this.mvc.perform(
                post("/admin/lotteries")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void givenUserIsAdmin_whenCreateLottery_thenResponseCorrectlyWithStatusCreated() throws Exception {
        when(this.lotteryService.createLottery(any())).thenReturn(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(80)
                        .amount(2)
                        .build()
        );

        this.mvc.perform(
                post("/admin/lotteries")
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {
                                    "ticket": "123456",
                                    "price": 80,
                                    "amount": 1
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value("123456"));
    }
}