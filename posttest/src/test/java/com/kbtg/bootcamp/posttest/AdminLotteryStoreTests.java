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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "username", roles = {"ADMIN"})
public class AdminLotteryStoreTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DirtiesContext
    @DisplayName("When admin creates lottery, then it is stored")
    void whenAdminCreatesLotteryThenItIsStored() throws Exception {
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value("123456"));

        this.mvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets").value(containsInAnyOrder("123456")));
    }
}
