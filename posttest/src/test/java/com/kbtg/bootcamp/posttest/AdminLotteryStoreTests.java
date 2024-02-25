package com.kbtg.bootcamp.posttest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLotteryStoreTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DirtiesContext
    @WithMockUser(username = "username", roles = {"ADMIN"})
    @DisplayName("Given create lottery request when create lottery then the lottery is stored")
    void givenCreateLotteryRequest_whenCreateLottery_thenTheLotteryIsStored() throws Exception {
        this.mvc.perform(
                post("/admin/lotteries")
                        .contentType("application/json")
                        .content("""
                                {
                                	"ticket": "222333",
                                	"price": 80,
                                	"amount": 1
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value("222333"));

        this.mvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").value(anyOf(hasItem("222333"))));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Given create lottery request when create lottery with anonymous user then response status is unauthorized")
    void givenCreateLotteryRequest_whenCreateLotteryWithAnonymousUser_thenResponseStatusIsUnauthorized() throws Exception {
        this.mvc.perform(
                        post("/admin/lotteries")
                                .contentType("application/json")
                                .content("""
                                {
                                	"ticket": "222333",
                                	"price": 80,
                                	"amount": 1
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "abcdefg123", roles = {"USER"})
    void givenCreateLotteryRequest_whenCreateLotteryWithUser_thenResponseStatusIsForbidden() throws Exception {
        this.mvc.perform(
                        post("/admin/lotteries")
                                .contentType("application/json")
                                .content("""
                                {
                                	"ticket": "222333",
                                	"price": 80,
                                	"amount": 1
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Given lottery when get lotteries then return lotteries")
    void givenLottery_whenGetLotteries_thenReturnLotteries() throws Exception {
        this.mvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(3)));
    }

    @Test
    @DirtiesContext
    @DisplayName("Given user id and ticket id when user buy lottery then the lottery is bought")
    void givenUserIdAndTicketId_whenUserBuyLottery_thenTheLotteryIsBought() throws Exception {
        this.mvc.perform(post("/users/username/lotteries/123456"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // should query database to check
        this.mvc.perform(get("/users/username/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").value(anyOf(hasItem("123456"))))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.cost").value(160));
    }

    @Test
    @DisplayName("Given user id and ticket id when lottery not found then response status is not found")
    void givenUserIdAndTicketId_whenLotteryNotFound_thenResponseStatusIsNotFound() throws Exception {
        this.mvc.perform(post("/users/username/lotteries/43212"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given user id and ticket id when lottery is found but sold out then response status is bad request")
    void givenUserIdAndTicketId_whenLotteryIsFoundButSoldOut_thenResponseStatusIsBadRequest() throws Exception {
        this.mvc.perform(post("/users/username/lotteries/654321"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When user list their lotteries then response with all lotteries they have with status ok")
    void whenUserListTheirLotteries_thenResponseWithAllLotteriesTheyHaveWithStatusOk() throws Exception {
        this.mvc.perform(get("/users/username/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").isArray())
                .andExpect(jsonPath("$.tickets").value(containsInAnyOrder("123456")))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.cost").value(80))
                .andExpect(jsonPath("$.tickets.length()").value(1));
    }
}
