//package com.kbtg.bootcamp.posttest.controller;
//
//import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
//import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class AdminLotteryControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ImpLotteryService impLotteryService;
//
//    @InjectMocks
//    private AdminLotteryController adminLotteryController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(adminLotteryController).build();
//    }
//
//    @Test
//    public void testFindAllLottery() throws Exception {
//        // Mocking the service behavior
//        LotteryEntity lottery1 = new LotteryEntity(/* */);
//        LotteryEntity lottery2 = new LotteryEntity(/*  */);
//        List<LotteryEntity> lotteryList = Arrays.asList(lottery1, lottery2);
//        when(impLotteryService.getAllLottery()).thenReturn(lotteryList);
//
//        // Performing the MockMvc request
//        mockMvc.perform(get("/admin/lotteries"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].ticket").value(/* expected value of ticket property for lottery1 */))
//                .andExpect(jsonPath("$[1].ticket").value(/* expected value of ticket property for lottery2 */));
//
//        // Verifying that the service method was called once
//        verify(impLotteryService, times(1)).getAllLottery();
//        verifyNoMoreInteractions(impLotteryService);
//    }
//
//    @Test
//    public void testAddLotteryToStore() throws Exception {
//        // Creating a LotteryEntity object for request body
//        LotteryEntity lotteryEntity = new LotteryEntity(/* provide necessary constructor arguments */);
//
//        // Performing the MockMvc request
//        mockMvc.perform(post("/admin/lotteries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(/* JSON representation of lotteryEntity */))
//                .andExpect(status().isOk())
//                .andExpect(content().string("\"ticket\": \"" + lotteryEntity.getTicket() + "\""));
//
//        // Verifying that the service method was called once with the correct argument
//        verify(impLotteryService, times(1)).addLotteryToStore(lotteryEntity);
//        verifyNoMoreInteractions(impLotteryService);
//    }
//}
//
