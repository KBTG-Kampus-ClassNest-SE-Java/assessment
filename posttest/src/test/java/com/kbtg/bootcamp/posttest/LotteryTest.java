package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryController;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class LotteryTest {

    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on GET: /lotteries should return list of lotteries with key ticket")
    void getListOfLotteries() throws Exception {
        Lottery lotteryDummy1 = new Lottery();
        lotteryDummy1.setTicket("123456");

        Lottery lotteryDummy2 = new Lottery();
        lotteryDummy2.setTicket("654321");

        Lottery lotteryDummy3 = new Lottery();
        lotteryDummy3.setTicket("789456");

        when(lotteryService.getLottery()).thenReturn(Map.of("ticket",
                List.of(lotteryDummy1.getTicket(),
                        lotteryDummy2.getTicket(),
                        lotteryDummy3.getTicket()
                )));

        String expectLottery1 = "123456";
        String expectLottery2 = "654321";
        String expectLottery3 = "789456";

        List<String> expectResult = List.of(expectLottery1, expectLottery2, expectLottery3);

        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.ticket", is(expectResult)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GET: /lotteries in case no any ticket in database should return with empty list")
    void getListOfLotteriesWhenNoAnyTicket() throws Exception {

        Map<String, List<String>> actualResult = new HashMap<>();
        actualResult.put("ticket", List.of());

        when(lotteryService.getLottery()).thenReturn(actualResult);

        List<String> expectResult = List.of();

        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.ticket", is(expectResult)))
                .andExpect(status().isOk());
    }
}
