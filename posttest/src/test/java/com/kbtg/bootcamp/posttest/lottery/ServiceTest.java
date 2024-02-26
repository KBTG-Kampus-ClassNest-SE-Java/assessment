package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicket;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketInfoResponseDTO;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTickerRepository;
import com.kbtg.bootcamp.posttest.services.UsersSerivce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    LotteryRepository lotteryRepository;
    @Mock
    UserTickerRepository userTickerRepository;

    private UsersSerivce usersSerivce;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usersSerivce = new UsersSerivce(lotteryRepository, userTickerRepository);
    }

    @Test
    @DisplayName("When bought 2 tickets, should return tickets = [\"123456\", \"123456\"], count = 2 and cost = 160.0")
    void getAllUserLotteriesInfo() throws Exception{
        // Arrange
        String userId = "1234567890";
        String ticketId1 = "123456";

        Lottery lottery1 = new Lottery(ticketId1, 80.0, 2);
        UserTicket userTicket1 = new UserTicket(userId, lottery1);
        UserTicket userTicket2 = new UserTicket(userId, lottery1);

        when(userTickerRepository.findByUserId(userId))
                .thenReturn(List.of(userTicket1, userTicket2));

        UserTicketInfoResponseDTO expected = new UserTicketInfoResponseDTO(
                List.of(ticketId1, ticketId1), 2, 160.0
        );

        // act
        UserTicketInfoResponseDTO result = usersSerivce.getAllUserLotteriesInfo(userId);

        // assert
        verify(userTickerRepository).findByUserId(userId);
        assertEquals(expected.getCount(), result.getCount());
        assertEquals(expected.getTickets(), result.getTickets());
        assertEquals(expected.getCost(), result.getCost());
    }

    @Test
    @DisplayName("Return ticket ID when sold user ticket successfully")
    void sellUserTicket() throws Exception{

        String userId = "1234567890";
        String ticketId1 = "123456";

        when(userTickerRepository.deleteByUserIdAndLotteryTicket(userId, ticketId1))
                .thenReturn(1);
        TicketIdResponseDTO expected = new TicketIdResponseDTO(ticketId1);

        TicketIdResponseDTO result = usersSerivce.sellUserTicket(userId, ticketId1);
        verify(userTickerRepository).deleteByUserIdAndLotteryTicket(userId, ticketId1);
        assertEquals(expected.getTicket(), result.getTicket());
    }
    @Test
    @DisplayName("Throw exception when user has no ticket asked to sell")
    void throwSellUserTicket() throws Exception{
        // Arrange
        String userId = "1234567890";
        String ticketId1 = "123456";

        when(userTickerRepository.deleteByUserIdAndLotteryTicket(userId, ticketId1))
                .thenReturn(0);


        // act and assert
        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> usersSerivce.sellUserTicket(userId, ticketId1)
        );
        verify(userTickerRepository).deleteByUserIdAndLotteryTicket(userId, ticketId1);
        assertEquals("No lottery to sell for this user.", exception.getMessage());
    }
    @Test
    @DisplayName("User buying lottery that has 1 ticket left should get user ticket ID of new saved ticket")
    void buyTicket() throws Exception{
        // Arrange
        String userId = "1234567890";
        String ticketId1 = "123456";

        Lottery lottery1 = new Lottery(ticketId1, 80.0, 2);
        UserTicket userTicket1 = new UserTicket(userId, lottery1);
        userTicket1.setId(Long.valueOf(1));
        UserTicket userTicket2 = new UserTicket(userId, lottery1);
        userTicket2.setId(Long.valueOf(2));

        lottery1.setUserTickets(List.of(userTicket1));

        when(lotteryRepository.findByTicket(ticketId1))
                .thenReturn(Optional.of(lottery1));

        when(userTickerRepository.save(any())).thenReturn(userTicket2);
        UserTicketIdResponseDTO expected = new UserTicketIdResponseDTO(userTicket2.getId().toString());

        // act
        UserTicketIdResponseDTO result = usersSerivce.buyTicket(userId, ticketId1);

        // assert
        verify(lotteryRepository).findByTicket(ticketId1);
        verify(userTickerRepository, times(1)).save(any());
        assertEquals(expected.getId(), result.getId());

    }

}
