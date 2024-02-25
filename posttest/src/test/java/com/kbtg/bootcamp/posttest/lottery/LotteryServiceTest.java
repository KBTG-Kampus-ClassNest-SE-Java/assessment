package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.StatusInternalServerErrorException;
import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Optional.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {
    @InjectMocks
    private LotteryService lotteryService;
    @Mock
    private LotteryRepository lotteryRepository;

    @Test
    @DisplayName("Create lottery should success and return ticket Id as string")
    public void TestCreateLotterySuccess() {
        String ticket = "123456";
        Integer price = 80;
        Integer amount = 1;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, amount, price);
        when(lotteryRepository.findByTicket(ticket)).thenReturn(empty());

        String result = lotteryService.createLottery(lotteryRequestDto);

        assertEquals(ticket, result);

        verify(lotteryRepository).findByTicket(ticket);
        verify(lotteryRepository).save(any(Lottery.class));
    }

    @Test
    @DisplayName("Create lottery that already exists should throw StatusInternalServerErrorException")
    public void TestCreateDuplicateTicket() {
        String ticket = "123456";
        Integer price = 80;
        Integer amount = 1;

        LotteryRequestDto lotteryRequestDto = new LotteryRequestDto(ticket, amount, price);

        when(lotteryRepository.findByTicket(ticket)).thenReturn(Optional.of(new Lottery(ticket, amount, price)));
        Lottery lottery = Optional.of(new Lottery(ticket, amount, price)).get();

        assertThrows(StatusInternalServerErrorException.class, () -> lotteryService.createLottery(lotteryRequestDto));

        verify(lotteryRepository).findByTicket(ticket);
        verify(lotteryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Non Zero amount of Lottery should return List of Ticket")
    public void TestGetLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new Lottery("123456", 80, 1),
                new Lottery("123457", 80, 1)
        ));

        List<String> result = lotteryService.getLotteries();

        assertEquals(Arrays.asList("123456", "123457"), result);

        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Zero amount of Lottery should return Empty List")
    public void TestGetZeroLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new Lottery("123456", 80, 0),
                new Lottery("123457", 80, 0)
        ));

        List<String> result = lotteryService.getLotteries();

        assertEquals(Collections.emptyList(), result);
        assertNotNull(result);

        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Mixed Zero and Non Zero amount of Lottery should return List of Non Zero Ticket")
    public void TestGetMixedLottery() {
        when(lotteryRepository.findAll()).thenReturn(Arrays.asList(
                new Lottery("123456", 80, 0),
                new Lottery("123457", 80, 1)
        ));

        List<String> result = lotteryService.getLotteries();

        assertEquals(Arrays.asList("123457"), result);
        assertNotNull(result);

        verify(lotteryRepository).findAll();
    }

    @Test
    @DisplayName("Initial state should return Empty List")
    public void TestGetInitLottery() {
        when(lotteryRepository.findAll()).thenReturn(Collections.emptyList());

        List<String> result = lotteryService.getLotteries();

        assertEquals(Collections.emptyList(), result);
        assertNotNull(result);

        verify(lotteryRepository).findAll();
    }
}
//    @Test
//    @DisplayName("")
//    public void should_successfully_create_lottery() {
//        LotteryRequestDto dto = new LotteryRequestDto("123456",80,1);
//        Lottery lottery = new Lottery("123456",80,1);
//        Lottery saveLottery = new Lottery("123456",80,1);
//
//        when(lotteryRepository.findByTicket(dto.getTicket())).thenReturn(Optional.of(lottery));
//        when()
//        when(lotteryRepository.save(lottery)).thenReturn(saveLottery);
//
//        LotteryResponseDto responseDto = lotteryService.createLottery(dto);
//
//        assertEquals(dto.getTicket(), responseDto.getTicket());
//
////        verify(lotteryRepository).findAll();
//    }
//    @Test
//    @DisplayName("should_successfully_get_lotteries")
//    public void should_successfully_get_lotteries() {
//        List<String> lotteries = new ArrayList<>();
//        lotteries.add("123456");
//
//        when(lotteryRepository.getAllTicket()).thenReturn(lotteries);
//
//        LotteryListResponseDto responseDto = lotteryService.getLotteries();
//
//        assertEquals(lotteries.get(0), responseDto.getTickets());
//
//    }



//}
//
//    private Lottery lottery;
//    private LotteryRequestDto lotteryRequestDto;
//    private LotteryResponseDto lotteryResponseDto;
//
//    @BeforeEach
//    public void setup() {
//
//        lottery = new Lottery("123456",80,1);
//
//        lotteryRequestDto = new LotteryRequestDto("123456",80,1);
//
//        lotteryResponseDto = new LotteryResponseDto("123456");
//    }
//
//    // Unit test for createLottery method
//    @Test
//    @DisplayName("Test for the createLottery method should return a LotteryResponseDto")
//    public void givenLotteryRequestDto_whenCreateLottery_thenReturnLotteryResponseDto() {
//
//        when(lotteryRepository.findByTicket(lotteryRequestDto.getTicket()))
//                .thenReturn(Optional.empty());
//
//        when(lotteryRepository.save(lottery)).thenReturn(lottery);
//
//        LotteryResponseDto actual = lotteryService.createLottery(lotteryRequestDto);
//
//        assertEquals(actual, lotteryResponseDto);
//    }
//    @Test
//    @DisplayName("Test for the createLottery method should throws an StatusInternalServerErrorException")
//    public void givenExistingTicket_whenRegisterTicket_thenThrowsException() {
//
//        when(lotteryRepository.findByTicket(lotteryRequestDto.getTicket()))
//                .thenReturn(Optional.of(lottery));
//
//        assertThrows(StatusInternalServerErrorException.class,
//                () -> {lotteryService.createLottery(lotteryRequestDto);
//                });
//
//        verify(lotteryRepository, never()).save(any(Lottery.class));
//    }
//    // Unit test for listAvailableTickets method
//    @Test
//    @DisplayName("Test for the listAvailableTickets method should return a TicketsResponse")
//    public void whenListAvailableTickets_thenReturnTicketsResponse() {
//
//        List<Lottery> lotteries = new ArrayList<Lottery>();
//        lotteries.add(new Lottery("000001", 80, 1));
//        lotteries.add(new Lottery("000002", 80, 1));
//        lotteries.add(new Lottery("000003", 80, 1));
//
//
//        when(lotteryRepository.getAllTicket()).thenReturn(lotteries);
//        LotteryListResponseDto actual = lotteryService.getLotteries();
//
//        assertThat(actual).isNotNull();
//        assertThat(actual.getTickets().size()).isEqualTo(3);
//        assertThat(actual.getTickets().get(0)).isEqualTo("123456");
//    }
//
////    @Test
////    void createLottery() {
////    }
////
////    @Test
////    void getLotteries() {
////    }
//}