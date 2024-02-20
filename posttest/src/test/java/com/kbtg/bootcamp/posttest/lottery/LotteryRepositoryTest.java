package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LotteryRepositoryTest {
    @Autowired
    private LotteryRepository lotteryRepository;


    @Test
    @DisplayName("EXP01 task: check Duplicate")
    void whenDuplicateTicket_thenThrowDataIntegrityViolationException() {
        // Arrange
        String ticket = "999999";

        // Act & Assert
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Lottery lottery2 = new Lottery(ticket, 20.0, 200L); // Duplicate ticket value
            lotteryRepository.save(lottery2);
        });

        // Assert
        assertThat(exception.getMessage()).containsIgnoringCase("duplicate key value violates unique constraint");
        assertThat(exception.getMessage()).containsIgnoringCase("lottery_ticket_key");
    }


    @Test
    public void whenTicketDoesNotExist_thenReturnFalse() {

        // Arrange
        String nonExistingTicket = "999999";

        // Act
        boolean exists = lotteryRepository.existsByTicket(nonExistingTicket);

        // Assert
        assertThat(exists).isFalse();
    }

}