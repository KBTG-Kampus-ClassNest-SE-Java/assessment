package com.kbtg.bootcamp.posttest.unittest.services;

import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.exceptions.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exceptions.LotterySoldOutException;
import com.kbtg.bootcamp.posttest.exceptions.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import com.kbtg.bootcamp.posttest.services.UserTicketServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserTicketServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private UserTicketServiceImpl userTicketService;

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
                .userId("2222233334")
                .lottery(lottery)
                .amount(1)
                .build();

        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery));

        when(this.userTicketRepository.existsByUserIdAndTicketId(anyString(), anyString())).thenReturn(true);

        when(this.userTicketRepository.updateTicketAmountOfUser(anyString(), anyString(), eq(1))).thenReturn(userTicket);

        UserTicket resultUserTicket = this.userTicketService.buyLottery("2222233334", "123456");

        assertThat(resultUserTicket).isNotNull();
        assertThat(resultUserTicket.getId()).isEqualTo(1);
        assertThat(resultUserTicket.getUserId()).isEqualTo("2222233334");
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
                .userId("2222233334")
                .amount(1)
                .lottery(lottery)
                .build();

        when(this.lotteryRepository.findById(anyString())).thenReturn(Optional.of(lottery));

        when(this.userTicketRepository.existsByUserIdAndTicketId(anyString(), anyString())).thenReturn(false);

        when(this.userTicketRepository.save(notNull())).thenReturn(userTicket);

        UserTicket resultUserTicket = this.userTicketService.buyLottery("2222233334", "123456");

        assertThat(resultUserTicket).isNotNull();
        assertThat(resultUserTicket.getId()).isEqualTo(1);
        assertThat(resultUserTicket.getUserId()).isEqualTo("2222233334");
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
                .isThrownBy(() -> this.userTicketService.buyLottery("2222233334", "123456"));
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
                .isThrownBy(() -> this.userTicketService.buyLottery("2222233334", "123456"));
    }

    @Test
    void whenGetLotteriesByUserId_thenReturnLotteryDetails() {
        when(this.userTicketRepository.findByUserId(anyString())).thenReturn(List.of(
                UserTicket
                        .builder()
                        .userId("2222233334")
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
                        .userId("2222233334")
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

        GetLotteriesByUserIdResponse response = this.userTicketService.getLotteriesByUserId("2222233334");

        assertThat(response).isNotNull();
        assertThat(response.tickets()).containsExactlyInAnyOrder("123456", "123456", "000567");
        assertThat(response.count()).isEqualTo(3);
        assertThat(response.cost()).isEqualTo(280);
    }

    @Test
    @DisplayName("Given user id and ticket id, when sell lottery, then return ticket id")
    void givenUserIdAndTicketId_whenSellLottery_thenReturnTicketId() {
        when(this.userTicketRepository.findByUserIdAndTicketId(anyString(), anyString())).thenReturn(Optional.of(
                UserTicket.builder()
                        .id(1)
                        .userId("2222233334")
                        .amount(2)
                        .lottery(
                                Lottery.builder()
                                        .ticket("000001")
                                        .price(100)
                                        .amount(1)
                                        .build()
                        )
                        .build()
        ));

        String ticketId = this.userTicketService.sellLottery("2222233334", "000001");

        assertThat(ticketId).isNotNull();
        assertThat(ticketId).isEqualTo("000001");
    }

    @Test
    @DisplayName("Given user id and ticket id, when sell lottery and user ticket is not found, then throw user ticket not found exception")
    void givenUserIdAndTicketId_whenSellLotteryAndUserTicketIsNotFound_thenThrowUserTicketNotFoundException() {
        when(this.userTicketRepository.findByUserIdAndTicketId(anyString(), anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserTicketNotFoundException.class)
                .isThrownBy(() -> this.userTicketService.sellLottery("2222233334", "000001"));
    }

}