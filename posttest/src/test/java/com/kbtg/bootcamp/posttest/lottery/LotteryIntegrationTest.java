package com.kbtg.bootcamp.posttest.lottery;

import com.jayway.jsonpath.JsonPath;
import com.kbtg.bootcamp.posttest.controllers.AdminController;
import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.services.AdminService;
import com.kbtg.bootcamp.posttest.services.UsersSerivce;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LotteryIntegrationTest {


     @Autowired
     private MockMvc mockMvc;

     @Autowired
     private LotteryRepository lotteryRepository;

     @Test
     @DisplayName("when create lottery on POST: /admin/lotteries should return status 201 and lottery should be saved in database")
     void createLotteryAndSave() throws Exception {

          String auth = "admin:password";
          String encodedAuth = Base64.encodeBase64String(auth.getBytes(StandardCharsets.UTF_8));
          String authHeader = "Basic " + encodedAuth;

         MvcResult mvcResult = mockMvc.perform(post("/admin/lotteries")
         .header("Authorization", authHeader)
         .contentType(MediaType.APPLICATION_JSON)
         .content(
         """
            {
            "ticket": "123456",
            "price": 80,
            "amount": 1
            }
         """))
         .andExpect(status().isCreated())
         .andExpect(jsonPath("$.ticket").value("123456"))
         .andReturn();

         // Extract JSON response body
         String jsonResponse = mvcResult.getResponse().getContentAsString();

         // Use JsonPath to extract data
         String ticket = JsonPath.read(jsonResponse, "$.ticket");

         Optional<Lottery> lottery = lotteryRepository.findByTicket(ticket);
         assert lottery.isPresent();
         assert lottery.get().getTicket().equals(ticket);
         assert lottery.get().getAmount().equals(1);
         assert lottery.get().getPrice().equals(Double.valueOf(80));

         // clean up after insert
         lotteryRepository.delete(lottery.get());
         Optional<Lottery> deletedLottery = lotteryRepository.findByTicket(ticket);
         assert deletedLottery.isEmpty();
     }



}
