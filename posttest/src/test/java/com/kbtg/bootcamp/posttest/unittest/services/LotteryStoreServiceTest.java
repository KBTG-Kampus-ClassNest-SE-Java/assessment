package com.kbtg.bootcamp.posttest.unittest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.services.LotteryStoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryStoreServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryStoreServiceImpl lotteryService;

    @Test
    @DisplayName("Given lottery, when create lottery, then the lottery is created")
    void givenLottery_whenCreateLottery_thenTheLotteryIsCreated() {
        CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest("123456", 80, 1);

        Lottery newLottery = this.lotteryService.createLottery(createLotteryRequest);

        assertThat(newLottery).isNotNull();
        assertThat(newLottery.getTicket()).isEqualTo("123456");
        assertThat(newLottery.getPrice()).isEqualTo(80);
        assertThat(newLottery.getAmount()).isEqualTo(1);
    }

    @Test
    @DisplayName("When lotteries size is greater than 0, then return lotteries")
    void whenLotteriesSizeIsGreaterThan0_thenReturnLotteries() {
        when(this.lotteryRepository.findByAmountGreaterThan0()).thenReturn(List.of(
            Lottery.builder()
                    .ticket("123456")
                    .price(80)
                    .amount(1)
                    .build(),
            Lottery.builder()
                    .ticket("000567")
                    .price(100)
                    .amount(2)
                    .build()
        ));

        List<Lottery> lotteries = this.lotteryService.getAvailableLotteries();

        assertThat(lotteries).isNotNull();
        assertThat(lotteries).hasSize(2);

        assertThat(lotteries.get(0).getTicket()).isEqualTo("123456");
        assertThat(lotteries.get(0).getPrice()).isEqualTo(80);
        assertThat(lotteries.get(0).getAmount()).isEqualTo(1);

        assertThat(lotteries.get(1).getTicket()).isEqualTo("000567");
        assertThat(lotteries.get(1).getPrice()).isEqualTo(100);
        assertThat(lotteries.get(1).getAmount()).isEqualTo(2);
    }

    @Test
    @DisplayName("When lotteries size is 0, then return lotteries is not null")
    void whenLotteriesSizeIs0_thenReturnLotteriesIsNotNull() {
        when(this.lotteryRepository.findByAmountGreaterThan0()).thenReturn(List.of());

        List<Lottery> lotteries = this.lotteryService.getAvailableLotteries();

        assertThat(lotteries).isNotNull();
        assertThat(lotteries).hasSize(0);
    }

}