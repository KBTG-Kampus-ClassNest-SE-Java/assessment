package com.kbtg.bootcamp.posttest.userticket.controller;

import com.kbtg.bootcamp.posttest.CustomWebSecurityConfigurerAdapterTest;
import com.kbtg.bootcamp.posttest.userticket.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userticket.service.UserTicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class,MockitoExtension.class})
@WebMvcTest({CustomWebSecurityConfigurerAdapterTest.class, UserTicketController.class})
class UserTicketControllerIntegrationTest {

    public static final String TICKET = "000001";
    public static final String USER_ID = "0881234567";
    public static final String USER_ID_INVALID = "abcdefghij";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTicketRepository userTicketRepository;

    @MockBean
    private UserTicketService userTicketService;

    @InjectMocks
    private UserTicketController userTicketController;

//    @BeforeEach
//    void setUp() {
//        UserTicketController userTicketController = new UserTicketController(userTicketService);
//        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController)
//                .alwaysDo(print())
//                .build();
//    }

    /**
     * Method under test: {@link UserTicketController#buyLotteries(String, String)}
     */
    @Test
    @DisplayName("should receive errors when userId is invalid")
    void walletMessage() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{userId}/lotteries", USER_ID_INVALID);

        // Act and Assert
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.errors",  hasSize(1)))
                .andExpect(jsonPath("$.errors",  hasItem("getLotteriesByUserId.userId: must be a number only  and start with '0'")))
                .andExpect(status().is(400));
    }
}
