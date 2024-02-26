package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LotteryRepositoryTest {
    @Autowired
    private LotteryRepository lotteryRepository;

    private Lottery lottery;

    @BeforeEach
    public void setupTestData() {
        lottery = new Lottery("000000", BigDecimal.valueOf(80), 1);
        lottery.setId(1);
        lottery.setCurrentAmount(lottery.getAmount());
    }

    @Test
    @DisplayName("Test save")
    public void save() {
        lotteryRepository.save(lottery);

        var existsLottery = lotteryRepository.findById(lottery.getId());

        assertThat(existsLottery).isNotNull();
    }

    @Test
    @DisplayName("Test find distinct tickets")
    public void findDistinctTickets() {
        var newLottery = new Lottery("000000", BigDecimal.valueOf(80), 3);
        newLottery.setId(2);
        newLottery.setCurrentAmount(newLottery.getAmount());

        lotteryRepository.save(newLottery);

        var tickets = lotteryRepository.findDistinctTickets();

        assertThat(tickets).isNotNull();
        assertThat(tickets.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Test find by id")
    public void findById() {
        var existsLottery = lotteryRepository.findById(lottery.getId());

        assertThat(existsLottery).isNotNull();
    }
}
