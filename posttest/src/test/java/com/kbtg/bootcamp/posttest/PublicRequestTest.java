package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.user.PublicController;
import com.kbtg.bootcamp.posttest.user.PublicService;
import com.kbtg.bootcamp.posttest.user.UserTicket;
import com.kbtg.bootcamp.posttest.user.Users;
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
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("when perform on GET: /lotteries should return list of lotteries with key ticket")
    void getListOfLotteries() throws Exception {
        Lottery lotteryDummy1 = new Lottery();
        lotteryDummy1.setTicket("123456");

        Lottery lotteryDummy2 = new Lottery();
        lotteryDummy2.setTicket("654321");

        Lottery lotteryDummy3 = new Lottery();
        lotteryDummy3.setTicket("789456");

        when(publicService.getLottery()).thenReturn(Map.of("ticket",
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

        when(publicService.getLottery()).thenReturn(Map.of("ticket", List.of()));

        List<String> expectResult = List.of();

        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.ticket", is(expectResult)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GET: http://localhost:8888/users/:user_id/lotteries/:lottery_id")
    void buyLotterySuccessShouldReturnIdOfUserTicket() throws Exception {
        String userId = "0000000002";
        Integer ticketId = 6;

        Users user = new Users();
        Lottery lottery = new Lottery();
        UserTicket userTicket = new UserTicket();
        userTicket.setUsers(user);
        userTicket.setLottery(lottery);
        userTicket.setId(1);

        Integer expectedValue = userTicket.getId();

        when(publicService.buyLottery(eq(userId), eq(ticketId))).thenReturn(Map.of("id", expectedValue));

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedValue)));
    }
}
