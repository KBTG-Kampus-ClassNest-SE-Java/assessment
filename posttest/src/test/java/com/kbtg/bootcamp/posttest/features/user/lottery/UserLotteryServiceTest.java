package com.kbtg.bootcamp.posttest.features.user.lottery;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.BadRequestException;
import com.kbtg.bootcamp.posttest.features.date.DateTimeProviderService;
import com.kbtg.bootcamp.posttest.features.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.buy_lottery.BuyLotteryResDto;
import com.kbtg.bootcamp.posttest.features.user.lottery.model.get_my_lottery.GetMyLotteryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserLotteryServiceTest {

    private UserLotteryService userLotteryService;

    private final LotteryRepository mockLotteryRepository = mock(LotteryRepository.class);

    private final UserTicketRepository mockUserTicketRepository = mock(UserTicketRepository.class);

    private final DateTimeProviderService mockDateTimeProviderService = mock(DateTimeProviderService.class);

    @BeforeEach
    public void setUp() {
        this.userLotteryService = spy(
                new UserLotteryService(
                        mockLotteryRepository,
                        mockUserTicketRepository,
                        mockDateTimeProviderService
                )
        );
    }

    // buy
    @Test
    public void Buy_ShouldThrowBadRequestException_WhenTicketIsNotAvailable() {
        // Arrange
        final String mockUserId = "1";
        final String mockTicketId = "123456";

        when(mockLotteryRepository.findAvailableLotteryByTicketId(any())).thenReturn(List.of());

        // Act
        assertThrows(BadRequestException.class, () -> {
            userLotteryService.buy(mockUserId, mockTicketId);
        });

        // Assert
        verify(mockLotteryRepository).findAvailableLotteryByTicketId(mockTicketId);
    }

    @Test
    public void Buy_ShouldSuccessAndDeleteLottery_WhenLotteryIsOutOfStockAfterBuy() {
        // Arrange
        final String mockUserId = "1";
        final String mockTicketId = "123456";
        final List<Lottery> mockAvailableLotteries = List.of(
                new Lottery(
                        null,
                        "123456",
                        1,
                        new BigDecimal("80")
                ),
                new Lottery(
                        null,
                        "123456",
                        1,
                        new BigDecimal("100")
                )
        );
        final UserTicket mockSaveUserTicketRes = new UserTicket(1);
        final LocalDateTime mockDateTime = LocalDateTime.now();

        when(mockLotteryRepository.findAvailableLotteryByTicketId(any())).thenReturn(mockAvailableLotteries);
        when(mockUserTicketRepository.save(any())).thenReturn(mockSaveUserTicketRes);
        when(mockDateTimeProviderService.getCurrentDateTime()).thenReturn(mockDateTime);

        Lottery cheapestLottery = mockAvailableLotteries.get(0);

        UserTicket mockUserTicket = new UserTicket();
        mockUserTicket.setUserId(mockUserId);
        mockUserTicket.setBuyPrice(cheapestLottery.getPrice());
        mockUserTicket.setTicketId(mockTicketId);
        mockUserTicket.setBuyDate(mockDateTime);

        // Act
        BuyLotteryResDto res = userLotteryService.buy(mockUserId, mockTicketId);

        // Assert
        verify(mockUserTicketRepository).save(mockUserTicket);
        verify(mockLotteryRepository).delete(cheapestLottery);
        assertEquals(mockSaveUserTicketRes.getId().toString(), res.id());
    }

    @Test
    public void Buy_ShouldSuccessAndSaveNewStockLottery_WhenRemainLotteryInStock() {
        // Arrange
        final String mockUserId = "1";
        final String mockTicketId = "123456";
        final List<Lottery> mockAvailableLotteries = List.of(
                new Lottery(
                        null,
                        "123456",
                        2,
                        new BigDecimal("80")
                ),
                new Lottery(
                        null,
                        "123456",
                        1,
                        new BigDecimal("100")
                )
        );
        final UserTicket mockSaveUserTicketRes = new UserTicket(1);
        final LocalDateTime mockDateTime = LocalDateTime.now();

        when(mockLotteryRepository.findAvailableLotteryByTicketId(any())).thenReturn(mockAvailableLotteries);
        when(mockUserTicketRepository.save(any())).thenReturn(mockSaveUserTicketRes);
        when(mockDateTimeProviderService.getCurrentDateTime()).thenReturn(mockDateTime);

        Lottery cheapestLottery = mockAvailableLotteries.get(0);

        UserTicket mockUserTicket = new UserTicket();
        mockUserTicket.setUserId(mockUserId);
        mockUserTicket.setBuyPrice(cheapestLottery.getPrice());
        mockUserTicket.setTicketId(mockTicketId);
        mockUserTicket.setBuyDate(mockDateTime);

        // Act
        BuyLotteryResDto res = userLotteryService.buy(mockUserId, mockTicketId);

        // Assert
        verify(mockUserTicketRepository).save(mockUserTicket);
        verify(mockLotteryRepository).save(cheapestLottery);
        assertEquals(mockSaveUserTicketRes.getId().toString(), res.id());
    }

    private static Stream<Arguments> GetMyLottery_ShouldSuccess_WhenCorrectRequest_DataSet() {
        return Stream.of(
                Arguments.of(
                        "1",
                        List.of(
                        ), new GetMyLotteryResDto(
                                List.of(),
                                0,
                                new BigDecimal("0")
                        )
                ),
                Arguments.of(
                        "12",
                        List.of(
                                new UserTicket(
                                        1,
                                        "12",
                                        "123456",
                                        new BigDecimal("100.50"),
                                        LocalDateTime.now()
                                ),
                                new UserTicket(
                                        1,
                                        "12",
                                        "333333",
                                        new BigDecimal("150"),
                                        LocalDateTime.now()
                                )
                        ), new GetMyLotteryResDto(
                                List.of("123456", "333333"),
                                2,
                                new BigDecimal("250.50")
                        )
                ),
                Arguments.of(
                        "1",
                        List.of(
                                new UserTicket(
                                        1,
                                        "1",
                                        "123456",
                                        new BigDecimal("100"),
                                        LocalDateTime.now()
                                ),
                                new UserTicket(
                                        1,
                                        "1",
                                        "123456",
                                        new BigDecimal("150"),
                                        LocalDateTime.now()
                                ),
                                new UserTicket(
                                        1,
                                        "1",
                                        "333333",
                                        new BigDecimal("100"),
                                        LocalDateTime.now()
                                )
                        ), new GetMyLotteryResDto(
                                List.of("123456", "123456", "333333"),
                                3,
                                new BigDecimal("350")
                        )
                )
        );
    }

    // get my lottery
    @ParameterizedTest
    @MethodSource("GetMyLottery_ShouldSuccess_WhenCorrectRequest_DataSet")
    public void GetMyLottery_ShouldSuccess_WhenCorrectRequest(String userId, List<UserTicket> mockMyLotteriesRes, GetMyLotteryResDto expected) {
        // Arrange
        when(mockUserTicketRepository.findByUserIdOrderByTicketIdAsc(any())).thenReturn(mockMyLotteriesRes);

        // Act
        GetMyLotteryResDto res = userLotteryService.getMyLottery(userId);

        // Assert
        verify(mockUserTicketRepository).findByUserIdOrderByTicketIdAsc(userId);
        assertEquals(expected, res);
    }
}