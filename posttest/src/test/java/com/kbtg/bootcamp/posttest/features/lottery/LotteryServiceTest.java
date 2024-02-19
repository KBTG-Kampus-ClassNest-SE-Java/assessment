package com.kbtg.bootcamp.posttest.features.lottery;

import com.kbtg.bootcamp.posttest.features.lottery.model.get_all_lottery.GetAllLotteryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LotteryServiceTest {

    private LotteryService lotteryService;
    private final LotteryRepository mockLotteryRepository = mock(LotteryRepository.class);

    @BeforeEach
    public void setUp() {
        this.lotteryService = spy(
                new LotteryService(mockLotteryRepository)
        );
    }

    // getAllLotteries

    @Test
    public void GetAllLotteries_ShouldReturnListOfAvailable_WhenRequestMethod() {
        // Arrange
        final List<String> mockFindAllAvailableLotteryRes = List.of("123456", "172052");
        final GetAllLotteryResDto expected = new GetAllLotteryResDto(
                mockFindAllAvailableLotteryRes
        );

        when(mockLotteryRepository.findAllAvailableLottery()).thenReturn(mockFindAllAvailableLotteryRes);

        // Act
        GetAllLotteryResDto res = lotteryService.getAllLotteries();

        // Assert
        assertEquals(expected, res);
        verify(mockLotteryRepository).findAllAvailableLottery();
    }
}