package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.lottery.request.SellLotteryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTicketServiceImplTest {

    @Mock
    private UserTicketRepository userTicketRepository;

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private UserTicketServiceImpl userTicketService;

    @Test
    public void testBuyLottery_whenLotteryDoesNotExist_thenNotFound() {
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userTicketService.buyLottery("1234567890", "ticket123");

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    public void testBuyLottery_Success() {
        String userId = "user123";
        String ticketId = "ticket456";

        Lottery lottery = new Lottery();
        lottery.setTicket(ticketId);
        lottery.setPrice(10);

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.of(lottery));

        UserTicket savedUserTicket = new UserTicket();
        savedUserTicket.setUserId(userId);
        savedUserTicket.setLottery(lottery);
        savedUserTicket.setAmount(1);
        savedUserTicket.setCost(lottery.getPrice());

        when(userTicketRepository.save(any(UserTicket.class))).thenReturn(savedUserTicket);

        ResponseEntity<Object> response = userTicketService.buyLottery(userId, ticketId);

        verify(userTicketRepository).save(any(UserTicket.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUserTicket, response.getBody());
    }

    @Test
    public void testBuyLottery_NotFound() {
        String userId = "user123";
        String ticketId = "nonExistentTicket";

        when(lotteryRepository.findByTicket(ticketId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userTicketService.buyLottery(userId, ticketId);

        verify(userTicketRepository, never()).save(any(UserTicket.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserLotteryTickets_whenUserTicketsExist_thenOk() {
        UserTicket userTicket1 = new UserTicket();
        userTicket1.setUserId("1234567890");
        Lottery lottery = new Lottery();
        lottery.setTicket("ticket123");
        lottery.setPrice(10);
        userTicket1.setLottery(lottery);
        userTicket1.setAmount(1);
        userTicket1.setCost(10);

        UserTicket userTicket2 = new UserTicket();
        Lottery lottery2 = new Lottery();
        lottery2.setTicket("ticket456");
        lottery2.setPrice(20);
        userTicket2.setUserId("1234567890");
        userTicket2.setLottery(lottery2);
        userTicket2.setAmount(2);
        userTicket2.setCost(20);

        when(userTicketRepository.findByUserId(anyString())).thenReturn(Arrays.asList(userTicket1, userTicket2));

        ResponseEntity<Map<String, Object>> response = userTicketService.getUserLotteryTickets("1234567890");

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("tickets", Arrays.asList("ticket123", "ticket456"));
        expectedResult.put("count", 2);
        expectedResult.put("cost", 50);
        assertEquals(ResponseEntity.ok(expectedResult), response);
    }

    @Test
    public void testGetUserLotteryTickets_whenNoUserTicketsExist_thenOk() {
        when(userTicketRepository.findByUserId(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = userTicketService.getUserLotteryTickets("1234567890");

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("tickets", Collections.emptyList());
        expectedResult.put("count", 0);
        expectedResult.put("cost", 0);
        assertEquals(ResponseEntity.ok(expectedResult), response);
    }

    @Test
    public void testSellLotteryTicket_whenUserTicketsExist_thenOk() {
        List<UserTicket> userTickets = getUserTickets();

        when(userTicketRepository.findByUserIdAndLotteryTicket("1234567890", "ticket123")).thenReturn(userTickets);

        ResponseEntity<SellLotteryResponse> response = userTicketService.sellLotteryTicket("1234567890", "ticket123");

        verify(userTicketRepository, times(1)).deleteUserTicketByUserIdAndLotteryTicket("1234567890", "ticket123");
        assertEquals(ResponseEntity.ok(new SellLotteryResponse("ticket123")), response);
    }

    private static List<UserTicket> getUserTickets() {
        Lottery lottery = new Lottery();
        lottery.setTicket("ticket123");
        lottery.setPrice(10);
        UserTicket userTicket1 = new UserTicket();
        userTicket1.setUserId("1234567890");
        userTicket1.setLottery(lottery);
        userTicket1.setAmount(1);
        userTicket1.setCost(10);
        UserTicket userTicket2 = new UserTicket();
        userTicket2.setUserId("1234567890");
        userTicket2.setLottery(lottery);
        userTicket2.setAmount(2);
        userTicket2.setCost(10);
        return Arrays.asList(userTicket1, userTicket2);
    }

    @Test
    public void testSellLotteryTicket_whenNoUserTicketsExist_thenNotFound() {

        lenient().when(userTicketRepository.findByUserIdAndLotteryTicket(anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<SellLotteryResponse> response = userTicketService.sellLotteryTicket("1234567890", "ticket123");

        verify(userTicketRepository, never()).deleteUserTicketByUserIdAndLotteryTicket("1234567890", "ticket123");
        assertEquals(ResponseEntity.notFound().build(), response);
        Mockito.validateMockitoUsage();
    }
}