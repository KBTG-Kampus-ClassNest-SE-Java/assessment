package com.kbtg.bootcamp.posttest.lottery.repo;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DataJpaTest
public class LotteryRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LotteryRepo lotteryRepo;

    @Test
    @Transactional
    void testFindById_ExistingTicket_ReturnsLottery() {

        Lottery lottery = new Lottery("123456", 10, 100);
        entityManager.persist(lottery);
        entityManager.flush();

        Optional<Lottery> found = lotteryRepo.findById("123456");

        assertTrue(found.isPresent());
        assertEquals("123456", found.get().getTicket());
    }

    @Test
    @Transactional
    void testFindById_NonExistingTicket_ReturnsEmptyOptional() {
        Optional<Lottery> found = lotteryRepo.findById("nonexistent");

        assertTrue(found.isEmpty());
    }

    @Test
    @Transactional
    void testFindAll_AmountGreaterThanZero_ReturnsLotteries() {
        Lottery lottery1 = new Lottery("123456", 10, 100);
        Lottery lottery2 = new Lottery("234567", 10, 100);
        entityManager.persist(lottery1);
        entityManager.persist(lottery2);
        entityManager.flush();

        List<Lottery> lotteries = lotteryRepo.findAll();

        assertEquals(2, lotteries.size());
        assertTrue(lotteries.stream().allMatch(l -> l.getAmount() > 0));
    }

    @Test
    @Transactional
    void testFindAll_ReturnsAllLotteries() {

        Lottery lottery1 = new Lottery("123456", 5, 10);
        Lottery lottery2 = new Lottery("654321", 10, 20);
        entityManager.persist(lottery1);
        entityManager.persist(lottery2);
        entityManager.flush();

        List<Lottery> lotteries = lotteryRepo.findAll();

        assertEquals(2, lotteries.size()); // Assert the expected count
        assertTrue(lotteries.contains(lottery1)); // Check for specific entities
        assertTrue(lotteries.contains(lottery2));
    }

    @Test
    @Transactional
    void testFindByAmount_ExistingAmount_ReturnsLotteries() {

        entityManager.persistAndFlush(new Lottery("111111", 10, 10));
        entityManager.persistAndFlush(new Lottery("222222", 5, 10));
        entityManager.persistAndFlush(new Lottery("333333", 10, 10));

        List<Lottery> foundLotteries = lotteryRepo.findByAmount(10); // findByAmount

        assertEquals(2, foundLotteries.size());
    }
}
