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
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        String randomString = String.valueOf(randomNumber);

        // Arrange
        String ticket = "111111";


        // Act & Assert
        DuplicateTickerException exception = assertThrows(DuplicateTickerException.class, () -> {
            Lottery lottery2 = new Lottery(ticket, 20.0, 200L); // Duplicate ticket value
            if (lotteryRepository.existsByTicket(ticket)) {
                throw new DuplicateTickerException("duplicate ticket");
            }
        });

    }


    @Test
    @DisplayName("EXP01 task: check notExits lottery")
    public void whenTicketDoesNotExist_thenReturnFalse() {
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        String randomString = String.valueOf(randomNumber);

        // Arrange
        String nonExistingTicket = "111111";

        // Act
        boolean exists = lotteryRepository.existsByTicket(nonExistingTicket);

        // Assert
        assertThat(exists).isFalse();
    }

}