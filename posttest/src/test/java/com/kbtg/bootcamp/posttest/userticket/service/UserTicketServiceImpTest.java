package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.exception.UserTicketException;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;

import com.kbtg.bootcamp.posttest.userticket.repo.UserTicketRepo;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserTicketServiceImp.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserTicketServiceImpTest {

    private static final int AMOUNT = 10;
    private static final int PRICE = 100;
    private static final String TICKET = "123456";
    private static final String USER_ID = "0123456789";

    @MockBean
    private LotteryRepo lotteryRepo;

    @MockBean
    private UserTicketRepo userTicketRepo;

    @Autowired
    private UserTicketServiceImp userTicketServiceImp;

    @Test
    @DisplayName("Should call findById 1 time and throw exception when the amount is 0")
    void testBuyLottery() {

        Lottery lottery = new Lottery();
        lottery.setAmount(0);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);
        Optional<Lottery> ofResult = Optional.of(lottery);
        when(lotteryRepo.findById(Mockito.any())).thenReturn(ofResult);

        assertThrows(UserTicketException.class, () -> userTicketServiceImp.buyLottery(USER_ID, TICKET));
        verify(lotteryRepo).findById(eq(TICKET));
    }

    @Test
    @DisplayName("Should have 0 ticket when the repo has 0 ticket")
    void testGetLotteryByUserId() {

        List<UserTicket> userTicketList = new ArrayList<>();
        when(userTicketRepo.findByUserId(Mockito.any())).thenReturn(userTicketList);

        UserTicketResDto actualLotteriesByUserId = userTicketServiceImp.getLotteryByUserId(USER_ID);

        verify(userTicketRepo).findByUserId(eq(USER_ID));
        assertEquals(0, actualLotteriesByUserId.getTotalPrice());
        assertEquals(0, actualLotteriesByUserId.getCount());
        assertEquals(userTicketList.size(), actualLotteriesByUserId.getTickets().size());
    }

    @Test
    @DisplayName("Should have 1 ticket and cost 100 with size = 1 when using getLotteryByUserId")
    void testGetLotteryByUserId2() {

        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        userTicket.setLottery(lottery);
        userTicket.setUserId(USER_ID);

        ArrayList<UserTicket> userTicketList = new ArrayList<>();
        userTicketList.add(userTicket);
        when(userTicketRepo.findByUserId(Mockito.any())).thenReturn(userTicketList);

        // Act
        UserTicketResDto actualLotteriesByUserId = userTicketServiceImp.getLotteryByUserId(USER_ID);

        // Assert
        verify(userTicketRepo).findByUserId(eq(USER_ID));
        assertEquals(100, actualLotteriesByUserId.getTotalPrice());
        assertEquals(1, actualLotteriesByUserId.getCount());
        assertEquals(1, actualLotteriesByUserId.getTickets().size());
    }


    @Test
    @DisplayName("Should cost 190 when buying 2 lotteries cost 100 and 90 ")
    void testGetLotteryByUserId3() {

        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        userTicket.setLottery(lottery);
        userTicket.setUserId(USER_ID);

        Lottery lottery2 = new Lottery();
        lottery2.setAmount(AMOUNT);
        lottery2.setPrice(90);
        lottery2.setTicket(USER_ID);

        UserTicket userTicket2 = new UserTicket();
        userTicket2.setId(2);
        userTicket2.setLottery(lottery2);
        userTicket2.setUserId(USER_ID);

        ArrayList<UserTicket> userTicketList = new ArrayList<>();
        userTicketList.add(userTicket2);
        userTicketList.add(userTicket);
        when(userTicketRepo.findByUserId(Mockito.any())).thenReturn(userTicketList);

        UserTicketResDto actualLotteriesByUserId = userTicketServiceImp.getLotteryByUserId(USER_ID);

        verify(userTicketRepo).findByUserId(eq(USER_ID));
        assertEquals(100 + 90, actualLotteriesByUserId.getTotalPrice());
        assertEquals(2, actualLotteriesByUserId.getCount());
        assertEquals(2, actualLotteriesByUserId.getTickets().size());
    }

    @Test
    @DisplayName("Should be null when deleting wrong user_id and ticket number")
    void testSellLottery() {

        when(userTicketRepo.findByUserId(Mockito.any())).thenReturn(new ArrayList<>());

        assertThrows(UserTicketException.class, () -> userTicketServiceImp.sellLottery(USER_ID, TICKET));
    }
}