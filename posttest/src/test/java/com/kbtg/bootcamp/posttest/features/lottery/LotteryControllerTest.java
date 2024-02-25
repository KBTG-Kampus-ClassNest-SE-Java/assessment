package com.kbtg.bootcamp.posttest.features.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.features.lottery.model.get_all_lottery.GetAllLotteryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LotteryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LotteryService mockLotteryService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String getAllLotteryUrl = "/lotteries";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // getAllLotteries
    @Test
    public void GetAllLotteries_ShouldSuccess_WhenRequestCorrectly() throws Exception {
        // Arrange
        final GetAllLotteryResDto mockRes = new GetAllLotteryResDto(
                List.of("152151", "215267")
        );
        final String expectedBodyJson = objectMapper.writeValueAsString(mockRes);
        when(mockLotteryService.getAllLotteries()).thenReturn(mockRes);

        // Act
        MvcResult mvcResult = mockMvc.perform(get(getAllLotteryUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expectedBodyJson, contentAsString);
        verify(mockLotteryService).getAllLotteries();
    }


}