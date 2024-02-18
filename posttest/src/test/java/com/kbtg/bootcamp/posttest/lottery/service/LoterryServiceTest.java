package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {LoterryService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LoterryServiceTest {
    public static final int AMOUNT = 1;
    public static final int PRICE = 80;
    public static final String TICKET = "000001";
    public static final String TICKET2 = "000002";

    @Autowired
    private LoterryService loterryService;

    @MockBean
    private LotteryRepository lotteryRepository;

    /**
     * Method under test: {@link LoterryService#save(LotteryRequestDto)}
     */
    @Test
    @DisplayName("before save check ticket from db 1 time")
    void testSave() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);
        Optional<Lottery> ofResult = Optional.of(lottery);
        when(lotteryRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> loterryService.save(new LotteryRequestDto()));
        verify(lotteryRepository, times(1)).findById(isNull());
    }

    /**
     * Method under test: {@link LoterryService#save(LotteryRequestDto)}
     */
    @Test
    @DisplayName("save success when data not exist")
    void testSave2() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);
        when(lotteryRepository.save(Mockito.<Lottery>any())).thenReturn(lottery);
        Optional<Lottery> emptyResult = Optional.empty();
        when(lotteryRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act
        TicketResponseDto actualSaveResult = loterryService.save(new LotteryRequestDto());

        // Assert
        verify(lotteryRepository).findById(isNull());
        verify(lotteryRepository).save(Mockito.<Lottery>any());
        assertEquals(TICKET, actualSaveResult.getTicket());
    }

    /**
     * Method under test: {@link LoterryService#save(LotteryRequestDto)}
     */
    @Test
    @DisplayName("save success when data not exist and return ticket")
    void testSaveSuccessWhenDataIfNotDuplicateOnDB() {
        // Arrange
        Lottery lottery = mock(Lottery.class);
        when(lottery.getTicket()).thenReturn(TICKET);
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);
        when(lotteryRepository.save(Mockito.<Lottery>any())).thenReturn(lottery);
        Optional<Lottery> emptyResult = Optional.empty();
        when(lotteryRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act
        TicketResponseDto actualSaveResult = loterryService.save(new LotteryRequestDto());

        // Assert
        verify(lotteryRepository).findById(isNull());
        verify(lotteryRepository).save(Mockito.<Lottery>any());
        assertEquals(TICKET, actualSaveResult.getTicket());
    }

    /**
     * Method under test: {@link LoterryService#findAll()}
     */
    @Test
    @DisplayName("service return all tickets when repo return all tickets")
    void testFindAll() {
        // Arrange
        ArrayList<Lottery> lotteryList = new ArrayList<>();
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        // Act
        TicketListResponseDto actualFindAllResult = loterryService.findAll();

        // Assert
        verify(lotteryRepository).findAll();
        assertEquals(lotteryList, actualFindAllResult.getTickets());
    }

    /**
     * Method under test: {@link LoterryService#findAll()}
     */
    @Test
    @DisplayName("service return 1 ticket when repo return 1 ticket and call repo 1 times")
    void testFindAll2() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        ArrayList<Lottery> lotteryList = new ArrayList<>();
        lotteryList.add(lottery);
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        // Act
        TicketListResponseDto actualFindAllResult = loterryService.findAll();

        // Assert
        verify(lotteryRepository).findAll();
        assertEquals(1, actualFindAllResult.getTickets().size());
    }

    /**
     * Method under test: {@link LoterryService#findAll()}
     */
    @Test
    @DisplayName("service return 2 ticket when repo return 2 ticket")
    void testFindAll3() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        Lottery lottery2 = new Lottery();
        lottery2.setAmount(AMOUNT);
        lottery2.setPrice(PRICE);
        lottery2.setTicket(TICKET2);

        ArrayList<Lottery> lotteryList = new ArrayList<>();
        lotteryList.add(lottery);
        lotteryList.add(lottery2);
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        // Act
        TicketListResponseDto actualFindAllResult = loterryService.findAll();

        // Assert
        verify(lotteryRepository).findAll();
        assertEquals(2, actualFindAllResult.getTickets().size());
    }

    /**
     * Method under test: {@link LoterryService#findAll()}
     */
    @Test
    @DisplayName("service return 1 ticket when repo return 1 ticket and call tickets size()")
    void testFindAllSholdCallTicketSizeForCreateDto() {
        // Arrange
        Lottery lottery = new Lottery();
        lottery.setAmount(AMOUNT);
        lottery.setPrice(PRICE);
        lottery.setTicket(TICKET);

        ArrayList<Lottery> lotteryList = new ArrayList<>();
        lotteryList.add(lottery);
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        // Act
        TicketListResponseDto actualFindAllResult = loterryService.findAll();

        // Assert
        verify(lotteryRepository).findAll();
        assertEquals(1, actualFindAllResult.getTickets().size());
    }
}
