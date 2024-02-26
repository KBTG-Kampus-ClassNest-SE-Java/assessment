package com.kbtg.bootcamp.posttest.unittest.controllers;

import com.kbtg.bootcamp.posttest.configs.SecurityConfig;
import com.kbtg.bootcamp.posttest.controllers.AdminController;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryStoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    private LotteryStoreService lotteryStoreService;

    @Test
    @WithAnonymousUser
    @DisplayName("Given user is anonymous when create lottery then response with status unauthorized")
    void givenUserIsAnonymous_whenCreateLottery_thenResponseWithStatusUnauthorized() throws Exception {
        this.mvc.perform(post("/admin/lotteries"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "notadmin", roles = "USER")
    @DisplayName("Given user is not admin when create lottery then response with status forbidden")
    void givenUserIsNotAdmin_whenCreateLottery_thenResponseWithStatusForbidden() throws Exception {
        this.mvc.perform(post("/admin/lotteries"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    @DisplayName("Given user is admin when create lottery then response correctly with status created")
    void givenUserIsAdmin_whenCreateLottery_thenResponseCorrectlyWithStatusCreated() throws Exception {
        when(this.lotteryStoreService.createLottery(any())).thenReturn(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(80)
                        .amount(2)
                        .build()
        );

        this.mvc.perform(
                post("/admin/lotteries")
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

    @Test
    @WithMockUser(username = "username", roles = {"ADMIN"})
    @DisplayName("Given user is admin when create lottery with incorrect data then response with status bad request")
    void givenUserIsAdmin_whenCreateLotteryWithIncorrectData_thenResponseWithStatusBadRequest() throws Exception {
        this.mvc.perform(
                post("/admin/lotteries")
                        .contentType("application/json")
                        .content("""
                                {
                                    "ticket": "12345",
                                    "price": -5,
                                    "amount": 12
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}