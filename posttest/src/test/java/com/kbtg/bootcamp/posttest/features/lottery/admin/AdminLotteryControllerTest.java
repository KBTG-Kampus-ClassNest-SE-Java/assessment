package com.kbtg.bootcamp.posttest.features.lottery.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryReqDto;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminLotteryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AdminLotteryService mockAdminLotteryService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String createTicketUrl = "/admin/lotteries";

    private final SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor mockAdminUser = user("admin").password("password").roles("ADMIN");

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // create ticket

    @Test
    public void CreateTicket_ShouldGotUnAuthorization_WhenAccessWithAnonymousUser() throws Exception {
        mockMvc.perform(post(createTicketUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void CreateTicket_ShouldGotUnAuthorization_WhenAccessWithUserNotHaveRoleAdmin() throws Exception {
        mockMvc.perform(post(createTicketUrl)
                        .with(user("customer").password("password").roles("CUSTOMER"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andReturn();
    }

    private static Stream<Arguments> CreateTicket_ShouldFailCauseValidateFail_WhenInvalidPayLoad_DataSet() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("{}"),
                Arguments.of("{\"ticket\": \"2222\"}"),
                Arguments.of("{\"ticket\": \"123456\"}"),
                Arguments.of("{\"ticket\":\"123456\",\"price\":0}"),
                Arguments.of("{\"ticket\":\"123456\",\"price\":200}"),
                Arguments.of("{\"ticket\":\"123456\",\"price\":200,\"amount\":0}")
        );
    }

    @ParameterizedTest
    @MethodSource("CreateTicket_ShouldFailCauseValidateFail_WhenInvalidPayLoad_DataSet")
    public void CreateTicket_ShouldFailCauseValidateFail_WhenInvalidPayLoad(String reqPayloadJson) throws Exception {
        mockMvc.perform(post(createTicketUrl)
                        .with(mockAdminUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqPayloadJson)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private static Stream<Arguments> CreateTicket_ShouldSuccess_WhenRequestCorrectly_DataSet() {
        return Stream.of(
                Arguments.of("{\"ticket\":\"123456\",\"price\":200, \"amount\":2}"),
                Arguments.of("{\"ticket\":\"721025\",\"price\":80, \"amount\":1}")
        );
    }

    @ParameterizedTest
    @MethodSource("CreateTicket_ShouldSuccess_WhenRequestCorrectly_DataSet")
    public void CreateTicket_ShouldSuccess_WhenRequestCorrectly(String reqPayloadJson) throws Exception {
        // Arrange
        final CreateLotteryReqDto mockReq = objectMapper.readValue(reqPayloadJson, CreateLotteryReqDto.class);
        final CreateLotteryResDto mockRes = new CreateLotteryResDto(
                mockReq.ticket()
        );
        final String expectedBodyJson = objectMapper.writeValueAsString(mockRes);
        when(mockAdminLotteryService.createLottery(any())).thenReturn(mockRes);

        // Act
        MvcResult mvcResult = mockMvc.perform(post(createTicketUrl)
                        .with(mockAdminUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqPayloadJson)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expectedBodyJson, contentAsString);
        verify(mockAdminLotteryService).createLottery(mockReq);
    }

}