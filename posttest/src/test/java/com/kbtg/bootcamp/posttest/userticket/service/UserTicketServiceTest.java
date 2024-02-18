package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.exception.LotteryUnavailableException;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.repository.UserTicketRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserTicketService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserTicketServiceTest {
    public static final int AMOUNT = 1;
    public static final int PRICE = 80;
    public static final String TICKET = "000001";
    public static final String TICKET2 = "000002";
    public static final String TICKET3 = "000003";
    public static final String USER_ID = "0881234567";

    @MockBean
    private LotteryRepository lotteryRepository;

    @MockBean
    private UserTicketRepository userTicketRepository;

    @Autowired
    private UserTicketService userTicketService;

    /**
     * Method under test: {@link UserTicketService#buyLotteries(String, String)}
     */
    @Test
    @DisplayName("should call findById 1 times and throw exception when amount is 0")
    void testBuyLotteries() {
        // สร้าง lottery ที่มี Amount 0
        // เมื่ buyLotteries จะได้ LotteryUnavailableException
        // verify ว่าได้ findById จริง ก่อนที่จะ error ว่า unavailable

        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(0);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);
        Optional<Lottery> ofResult = Optional.of(lottery);
        when(lotteryRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(LotteryUnavailableException.class, () -> userTicketService.buyLotteries(USER_ID, USER_ID));
        verify(lotteryRepository).findById(eq(USER_ID));
    }

    /**
     * Method under test: {@link UserTicketService#getLotteriesByUserId(String)}
     */
    @Test
    @DisplayName("should 0 ticket when repo is 0 ticket")
    void testGetLotteriesByUserId() {
        // Arrange
        ArrayList<UserTicket> userTicketList = new ArrayList<>();
        when(userTicketRepository.findByUserId(Mockito.<String>any())).thenReturn(userTicketList);

        // Act
        UserTickerSummaryDto actualLotteriesByUserId = userTicketService.getLotteriesByUserId(USER_ID);

        // Assert
        verify(userTicketRepository).findByUserId(eq(USER_ID));
        assertEquals(0, actualLotteriesByUserId.getCost().intValue());
        assertEquals(0, actualLotteriesByUserId.getCount().intValue());
        assertEquals(userTicketList, actualLotteriesByUserId.getTickets());
    }

    /**
     * Method under test: {@link UserTicketService#getLotteriesByUserId(String)}
     */
    @Test
    @DisplayName("should 1 ticket cost 80 size 1 when get by id1")
    void testGetLotteriesByUserId2() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1L);
        userTicket.setLottery(lottery);
        userTicket.setUserId(USER_ID);

        ArrayList<UserTicket> userTicketList = new ArrayList<>();
        userTicketList.add(userTicket);
        when(userTicketRepository.findByUserId(Mockito.<String>any())).thenReturn(userTicketList);

        // Act
        UserTickerSummaryDto actualLotteriesByUserId = userTicketService.getLotteriesByUserId(USER_ID);

        // Assert
        verify(userTicketRepository).findByUserId(eq(USER_ID));
        assertEquals(80, actualLotteriesByUserId.getCost().intValue());
        assertEquals(1, actualLotteriesByUserId.getCount().intValue());
        assertEquals(1, actualLotteriesByUserId.getTickets().size());
    }

    /**
     * Method under test: {@link UserTicketService#getLotteriesByUserId(String)}
     */
    @Test
    @DisplayName("should cost 170 when buy 2 lottery cost 80 and 90 ")
    void testGetLotteriesByUserId3() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1L);
        userTicket.setLottery(lottery);
        userTicket.setUserId(USER_ID);

        Lottery lottery2 = new Lottery();
        lottery2.setAmount(AMOUNT);
        lottery2.setPrice(90);
        lottery2.setTicket(USER_ID);

        UserTicket userTicket2 = new UserTicket();
        userTicket2.setId(2L);
        userTicket2.setLottery(lottery2);
        userTicket2.setUserId(USER_ID);

        ArrayList<UserTicket> userTicketList = new ArrayList<>();
        userTicketList.add(userTicket2);
        userTicketList.add(userTicket);
        when(userTicketRepository.findByUserId(Mockito.<String>any())).thenReturn(userTicketList);

        // Act
        UserTickerSummaryDto actualLotteriesByUserId = userTicketService.getLotteriesByUserId(USER_ID);

        // Assert
        verify(userTicketRepository).findByUserId(eq(USER_ID));
        assertEquals(80 + 90, actualLotteriesByUserId.getCost().intValue());
        assertEquals(2, actualLotteriesByUserId.getCount().intValue());
        assertEquals(2, actualLotteriesByUserId.getTickets().size());
    }

    /**
     * Method under test:
     * {@link UserTicketService#deleteLotteriesByUserId(String, String)}
     */
    @Test
    @DisplayName("should null when delete wrong user_id and ticket number")
    void testDeleteLotteriesByUserId() {
        // Arrange
        when(userTicketRepository.findByUserId(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        TicketResponseDto actualDeleteLotteriesByUserIdResult = userTicketService.deleteLotteriesByUserId(USER_ID, TICKET);

        // Assert
        verify(userTicketRepository).findByUserId(eq(USER_ID));
        assertNull(actualDeleteLotteriesByUserIdResult);
    }
}
