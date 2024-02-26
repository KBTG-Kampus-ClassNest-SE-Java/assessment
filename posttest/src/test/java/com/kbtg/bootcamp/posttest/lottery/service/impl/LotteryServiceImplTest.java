package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.lottery.Respone.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.request.AdminLotteryRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceImplTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryServiceImpl lotteryService;

    @Test
    public void testAddLottery_whenLotteryExists_thenBadRequest() {
        AdminLotteryRequest lotteryRequest = new AdminLotteryRequest("123456", 80, 1);
        Lottery existingLottery = new Lottery();
        existingLottery.setTicket("123456");
        when(lotteryRepository.findByTicket(lotteryRequest.ticket())).thenReturn(Optional.of(existingLottery));

        ResponseEntity<Object> response = lotteryService.addLottery(lotteryRequest);

        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    @Test
    public void testAddLottery_whenLotteryDoesNotExist_thenCreated() {
        AdminLotteryRequest lotteryRequest = new AdminLotteryRequest("123456", 80, 1);
        Lottery savedLottery = new Lottery();
        savedLottery.setTicket("123456");
        when(lotteryRepository.findByTicket(lotteryRequest.ticket())).thenReturn(Optional.empty());
        when(lotteryRepository.save(lotteryRequest.toLottery())).thenReturn(savedLottery);

        ResponseEntity<Object> response = lotteryService.addLottery(lotteryRequest);

        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(savedLottery), response);
    }

    @Test
    public void testGetAllLotteries_whenLotteriesExist_thenOk() {
        Lottery lottery1 = new Lottery();
        lottery1.setTicket("123456");
        Lottery lottery2 = new Lottery();
        lottery2.setTicket("654321");
        List<Lottery> lotteries = Arrays.asList(lottery1, lottery2);
        when(lotteryRepository.findAll()).thenReturn(lotteries);

        ResponseEntity<LotteryResponse> response = lotteryService.getAllLotteries();

        LotteryResponse expectedResponse = new LotteryResponse(Arrays.asList(lottery1.getTicket(), lottery2.getTicket()));
        assertEquals(ResponseEntity.ok(expectedResponse), response);
    }
}