package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {
	MockMvc mockMvc;
	@Mock
	LotteryService lotteryService;

	@BeforeEach
	void setUp() {
		LotteryController lotteryController = new LotteryController(lotteryService);
		mockMvc = MockMvcBuilders.standaloneSetup(lotteryController).alwaysDo(print()).build();
	}
	@Test
	@DisplayName("Should be retrieve lotteries and status OK (200)")
	void shouldRetrieveLottery() throws Exception {
		List<String> lotteries = new ArrayList<>();
		lotteries.add("000001");
		lotteries.add("000002");
		lotteries.add("123456");

		when(lotteryService.getLotteries()).thenReturn(lotteries);

		mockMvc.perform(get("/lotteries"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tickets",is(lotteries)))
				.andReturn();

	}
}
