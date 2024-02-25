package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.user.PublicController;
import com.kbtg.bootcamp.posttest.user.PublicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublicRequestTest {

    MockMvc mockMvc;
    @Mock
    PublicService publicService;

    @BeforeEach
    void Setup() {
        PublicController publicController = new PublicController(publicService);
        mockMvc = MockMvcBuilders.standaloneSetup(publicController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on GET: /lotteries should return list of lotteries")
    void getListOfLotteries() throws Exception {
        Lottery lottery1 = new Lottery();
        lottery1.setTicket("123456");

        Lottery lottery2 = new Lottery();
        lottery2.setTicket("654321");

        when(publicService.getLottery()).thenReturn(List.of(lottery1, lottery2));

        String expectLottery1 = "123456";
        String expectLottery2 = "654321";

        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$[0].ticket", is(expectLottery1)))
                .andExpect(jsonPath("$[1].ticket", is(expectLottery2)))
                .andExpect(status().isOk());
    }
}
