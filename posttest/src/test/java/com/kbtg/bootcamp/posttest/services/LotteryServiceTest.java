package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private LotteryServiceImpl lotteryService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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
        when(this.lotteryRepository.findAll()).thenReturn(List.of(
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

        List<Lottery> lotteries = this.lotteryService.getLotteries();

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
        when(this.lotteryRepository.findAll()).thenReturn(List.of());

        List<Lottery> lotteries = this.lotteryService.getLotteries();

        assertThat(lotteries).isNotNull();
        assertThat(lotteries).hasSize(0);
    }

    @Test
    @DisplayName("Given user id and ticket id, when buy lottery and user already have ticket data, then return user ticket")
    void givenUserIdAndTicketId_whenBuyLotteryAndUserAlreadyHaveTicketData_thenReturnUserTicket() {
        Lottery lottery = Lottery.builder()
                .ticket("123456")
                .price(100)
                .amount(2)
                .build();

        UserTicket userTicket = UserTicket.builder()
                .id(1)
                .userId("username")
                .lottery(lottery)
                .amount(1)
                .build();

        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery));

        when(this.userTicketRepository.existsByUserIdAndTicketId(anyString(), anyString())).thenReturn(true);

        when(this.userTicketRepository.updateTicketAmountOfUser(anyString(), anyString(), eq(1))).thenReturn(userTicket);

        UserTicket resultUserTicket = this.lotteryService.buyLottery("username", "123456");

        assertThat(resultUserTicket).isNotNull();
        assertThat(resultUserTicket.getId()).isEqualTo(1);
        assertThat(resultUserTicket.getUserId()).isEqualTo("username");
        assertThat(resultUserTicket.getAmount()).isEqualTo(1);

        assertThat(resultUserTicket.getLottery()).isNotNull();
        assertThat(resultUserTicket.getLottery().getTicket()).isEqualTo("123456");
        assertThat(resultUserTicket.getLottery().getPrice()).isEqualTo(100);
        assertThat(resultUserTicket.getLottery().getAmount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Given user id and ticket id, when buy lottery and user not have ticket data, then return user ticket")
    void givenUserIdAndTicketId_whenBuyLotteryAndUserNotHaveTicketData_thenReturnUserTicket() {
        Lottery lottery = Lottery.builder()
                .ticket("123456")
                .price(100)
                .amount(2)
                .build();

        UserTicket userTicket = UserTicket.builder()
                .id(1)
                .userId("username")
                .amount(1)
                .lottery(lottery)
                .build();

        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery));

        when(this.userTicketRepository.existsByUserIdAndTicketId(anyString(), anyString())).thenReturn(false);

        when(this.userTicketRepository.save(notNull())).thenReturn(userTicket);

        UserTicket resultUserTicket = this.lotteryService.buyLottery("username", "123456");

        assertThat(resultUserTicket).isNotNull();
        assertThat(resultUserTicket.getId()).isEqualTo(1);
        assertThat(resultUserTicket.getUserId()).isEqualTo("username");
        assertThat(resultUserTicket.getAmount()).isEqualTo(1);

        assertThat(resultUserTicket.getLottery()).isNotNull();
        assertThat(resultUserTicket.getLottery().getTicket()).isEqualTo("123456");
        assertThat(resultUserTicket.getLottery().getPrice()).isEqualTo(100);
        assertThat(resultUserTicket.getLottery().getAmount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Given user id and ticket id, when lottery is not found, then throw lottery not found exception")
    void givenUserIdAndTicketId_whenLotteryIsNotFound_thenThrowLotteryNotFoundException() {
        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(LotteryNotFoundException.class)
                .isThrownBy(() -> this.lotteryService.buyLottery("username", "123456"));
    }

    @Test
    @DisplayName("Given user id and ticket id, when lottery is found but sold out, then throw lottery sold out exception")
    void givenUserIdAndTicketId_whenLotteryIsFoundButSoldOut_thenThrowLotterySoldOutException() {
        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.of(
                Lottery
                        .builder()
                        .ticket("123456")
                        .price(100)
                        .amount(0)
                        .build()
        ));

        assertThatExceptionOfType(LotterySoldOutException.class)
                .isThrownBy(() -> this.lotteryService.buyLottery("username", "123456"));
    }

    @Test
    void whenGetLotteriesByUserId_thenReturnLotteryDetails() {
        when(this.userTicketRepository.findByUserId(anyString())).thenReturn(List.of(
                UserTicket
                        .builder()
                        .userId("username")
                        .amount(2)
                        .lottery(
                                Lottery
                                        .builder()
                                        .ticket("123456")
                                        .price(100)
                                        .amount(1)
                                        .build()
                        )
                        .build(),
                UserTicket
                        .builder()
                        .userId("username")
                        .amount(1)
                        .lottery(
                                Lottery
                                        .builder()
                                        .ticket("000567")
                                        .price(80)
                                        .amount(1)
                                        .build()
                        )
                        .build()
        ));

        GetLotteriesByUserIdResponse response = this.lotteryService.getLotteriesByUserId("username");

        assertThat(response).isNotNull();
        assertThat(response.tickets()).containsExactlyInAnyOrder("123456", "123456", "000567");
        assertThat(response.count()).isEqualTo(3);
        assertThat(response.cost()).isEqualTo(280);
    }
}