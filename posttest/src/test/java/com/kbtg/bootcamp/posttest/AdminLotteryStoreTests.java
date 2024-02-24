package com.kbtg.bootcamp.posttest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "username", roles = {"ADMIN"})
public class AdminLotteryStoreTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DirtiesContext
    @DisplayName("Given create lottery request when create lottery then the lottery is stored")
    void givenCreateLotteryRequest_whenCreateLottery_thenTheLotteryIsStored() throws Exception {
        this.mvc.perform(
                post("/admin/lotteries")
                        .with(csrf())
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
    @DisplayName("Given lottery when get lotteries then return lotteries")
    void givenLottery_whenGetLotteries_thenReturnLotteries() throws Exception {
        this.mvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(2)));
    }

    @Test
    @DisplayName("Given user id and ticket id when user buy lottery then the lottery is bought")
    void givenUserIdAndTicketId_whenUserBuyLottery_thenTheLotteryIsBought() throws Exception {
        this.mvc.perform(
                post("/users/username/lotteries/123456")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // query database to check if the lottery is bought
    }

    @Test
    @DisplayName("Given user id and ticket id when lottery not found then response status is not found")
    void givenUserIdAndTicketId_whenLotteryNotFound_thenResponseStatusIsNotFound() throws Exception {
        this.mvc.perform(
                post("/users/username/lotteries/43212")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given user id and ticket id when lottery is found but sold out then response status is bad request")
    void givenUserIdAndTicketId_whenLotteryIsFoundButSoldOut_thenResponseStatusIsBadRequest() throws Exception {
        this.mvc.perform(
                post("/users/username/lotteries/654321")
                        .with(csrf())
                )
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
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.cost").isNumber())
                .andExpect(jsonPath("$.tickets.length()").value(2));
    }
}
