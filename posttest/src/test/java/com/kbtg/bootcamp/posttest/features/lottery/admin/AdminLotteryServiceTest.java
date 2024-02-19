package com.kbtg.bootcamp.posttest.features.lottery.admin;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.features.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryReqDto;
import com.kbtg.bootcamp.posttest.features.lottery.admin.model.create_lottery.CreateLotteryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminLotteryServiceTest {

    private AdminLotteryService adminLotteryService;
    private final LotteryRepository lotteryRepository = mock(LotteryRepository.class);

    @BeforeEach
    public void setUp() {
        adminLotteryService = spy(
                new AdminLotteryService(
                        lotteryRepository
                )
        );
    }

    // createLottery
    @Test
    public void CreateLottery_ShouldCreateNewTicket_WhenNotExistTicketIdAndPrice() {
        // Arrange
        final CreateLotteryReqDto mockReq = new CreateLotteryReqDto(
                "120720",
                new BigDecimal("200.0"),
                1
        );

        final Lottery mockLottery = new Lottery();
        mockLottery.setTicketId(mockReq.ticket());
        mockLottery.setAmount(mockReq.amount());
        mockLottery.setPrice(mockReq.price());

        when(lotteryRepository.findByTicketIdAndPrice(any(), any())).thenReturn(Optional.empty());

        // Act
        final CreateLotteryResDto res = adminLotteryService.createLottery(mockReq);

        // Assert
        assertEquals(mockReq.ticket(), res.ticket());
        verify(lotteryRepository).findByTicketIdAndPrice(mockReq.ticket(), mockReq.price());
        verify(lotteryRepository).save(mockLottery);
    }

    @Test
    public void CreateLottery_ShouldUpdateQtyOnOldTicket_WhenExistTicketIdAndPrice() {
        // Arrange
        final CreateLotteryReqDto mockReq = new CreateLotteryReqDto(
                "120720",
                new BigDecimal("200.0"),
                1
        );

        final int mockOldAmount = 3;

        final Lottery oldLottery = new Lottery();
        oldLottery.setTicketId(mockReq.ticket());
        oldLottery.setAmount(mockOldAmount);
        oldLottery.setPrice(mockReq.price());

        final Lottery mockLottery = new Lottery();
        mockLottery.setTicketId(mockReq.ticket());
        mockLottery.setAmount(mockReq.amount() + mockOldAmount);
        mockLottery.setPrice(mockReq.price());

        when(lotteryRepository.findByTicketIdAndPrice(any(), any())).thenReturn(Optional.of(oldLottery));

        // Act
        final CreateLotteryResDto res = adminLotteryService.createLottery(mockReq);

        // Assert
        assertEquals(mockReq.ticket(), res.ticket());
        verify(lotteryRepository).findByTicketIdAndPrice(mockReq.ticket(), mockReq.price());
        verify(lotteryRepository).save(mockLottery);
    }

}