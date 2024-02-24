package com.kbtg.bootcamp.posttest.Repository;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LotteryRepositoryTest {
    @Autowired
    private LotteryRepository lotteryRepository;
    @Autowired
    private UserTicketRepository userTicketRepository;

    @Test
    @DisplayName("Find all lotteries")
    void testFindAll() {
        Lottery lottery1 = new Lottery("111111", 2L, 10.0);
        Lottery lottery2 = new Lottery("222222", 1L, 15.0);

        lotteryRepository.saveAll(List.of(lottery1, lottery2));

        List<Lottery> allLotteries = lotteryRepository.findAll();

        assertNotNull(allLotteries);
        assertEquals(2, allLotteries.size());

        assertTrue(allLotteries.stream().anyMatch(l -> "111111".equals(l.getTicket())));
        assertTrue(allLotteries.stream().anyMatch(l -> "222222".equals(l.getTicket())));
    }

    @Test
    @DisplayName("Find all lotteries but there is no lottery")
    void testFindAllButNoLottery() {
        List<Lottery> allLotteries = lotteryRepository.findAll();

        assertNotNull(allLotteries);
        assertEquals(0, allLotteries.size());
    }

    @Test
    @DisplayName("Delete a lottery")
    void testDelete() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);

        lotteryRepository.save(lottery);

        lotteryRepository.delete(lottery);

        Optional<Lottery> deletedLottery = lotteryRepository.findById(lottery.getId().intValue());

        assertTrue(deletedLottery.isEmpty());
    }

    @Test
    @DisplayName("Save a new lottery")
    void testSave() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);

        Lottery savedLottery = lotteryRepository.save(lottery);

        Optional<Lottery> retrievedLottery = lotteryRepository.findById(savedLottery.getId().intValue());

        assertTrue(retrievedLottery.isPresent());
        retrievedLottery.ifPresent(l -> {
            assertEquals("123456", l.getTicket());
        });
    }

    @Test
    @DisplayName("Save a new lottery with amount 0")
    void testSaveWithAmountZero() {
        Lottery lottery = new Lottery("123456", 0L, 10.0);

        Lottery savedLottery = lotteryRepository.save(lottery);

        Optional<Lottery> retrievedLottery = lotteryRepository.findById(savedLottery.getId().intValue());

        assertTrue(retrievedLottery.isPresent());
        retrievedLottery.ifPresent(l -> {
            assertEquals("123456", l.getTicket());
        });
    }

    @Test
    @DisplayName("Finding lottery by ticket ID")
    void TestFindByTicket() {
        Lottery lottery = new Lottery("123456", 0L, 10.0);

        lotteryRepository.save(lottery);

        Optional<Lottery> foundLottery = lotteryRepository.findByTicket("123456");

        assertTrue(foundLottery.isPresent());
        foundLottery.ifPresent(l -> assertEquals("123456", l.getTicket()));
    }

    @Test
    @DisplayName("Finding first lottery by ticket ID")
    void TestFindFirstByTicket() {
        Lottery lottery1 = new Lottery("111111", 1L, 10.0);
        Lottery lottery2 = new Lottery("222222", 1L, 15.0);

        lotteryRepository.saveAll(List.of(lottery1, lottery2));

        Optional<Lottery> foundLottery = lotteryRepository.findFirstByTicket("111111");

        assertTrue(foundLottery.isPresent());
        foundLottery.ifPresent(l -> assertEquals("111111", l.getTicket()));
    }

    @Test
    @DisplayName("Finding first lottery by ticket ID where its amount is 0")
    void TestFindFirstByTicketWhereAmountIsZero() {
        Lottery lottery1 = new Lottery("111111", 0L, 10.0);
        Lottery lottery2 = new Lottery("222222", 1L, 15.0);

        lotteryRepository.saveAll(List.of(lottery1, lottery2));

        Optional<Lottery> foundLottery = lotteryRepository.findFirstByTicket("111111");

        assertTrue(foundLottery.isPresent());
        foundLottery.ifPresent(l -> assertEquals("111111", l.getTicket()));
    }

    @Test
    @DisplayName("Finding lotteries by user ID")
    void testFindByUserTicketUserId() {
        Lottery lottery = new Lottery("333333", 0L, 10.0);

        UserTicket userTicket = new UserTicket("1234567890", "buy", 1L, lottery);

        lotteryRepository.save(lottery);
        userTicketRepository.save(userTicket);

        List<Lottery> foundLotteries = lotteryRepository.findByUserTicketUserId("1234567890");

        assertFalse(foundLotteries.isEmpty());
        assertEquals("333333", foundLotteries.get(0).getTicket());
    }
}