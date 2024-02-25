package com.kbtg.bootcamp.posttest.Repository;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository;
import org.junit.jupiter.api.BeforeEach;
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
class UserTicketRepositoryTest {
    @Autowired
    private UserTicketRepository userTicketRepository;
    @Autowired
    private LotteryRepository lotteryRepository;

    @BeforeEach
    void setUp() {
        lotteryRepository.deleteAll();
        userTicketRepository.deleteAll();
    }

    @Test
    @DisplayName("Delete a user ticket")
    void TestDelete() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);
        UserTicket userTicket = new UserTicket("1234567890", "123456", 1L, lottery);

        lotteryRepository.save(lottery);
        userTicketRepository.save(userTicket);

        userTicketRepository.delete(userTicket);

        Optional<UserTicket> foundUserTicket = userTicketRepository.findById(userTicket.getId().intValue());

        assertFalse(foundUserTicket.isPresent());
    }

    @Test
    @DisplayName("Save a user ticket")
    void TestSave() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);
        UserTicket userTicket = new UserTicket("1234567890", "123456", 1L, lottery);

        lotteryRepository.save(lottery);
        userTicketRepository.save(userTicket);

        Optional<UserTicket> foundUserTicket = userTicketRepository.findById(userTicket.getId().intValue());

        assertTrue(foundUserTicket.isPresent());
        foundUserTicket.ifPresent(ut -> {
            assertEquals("1234567890", ut.getUserId());
            assertEquals("123456", ut.getLottery().getTicket());
        });
    }

    @Test
    @DisplayName("Find user ticket by user ID and ticket ID")
    void TestFindByUserIdAndTicket() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);
        Lottery lottery1 = new Lottery("111111", 2L, 15.0);
        UserTicket userTicket = new UserTicket("1234567890", "123456", 1L, lottery);

        lotteryRepository.saveAll(List.of(lottery, lottery1));
        userTicketRepository.save(userTicket);

        Optional<UserTicket> foundUserTicket = userTicketRepository.findByUserIdAndTicket("1234567890", "123456");

        assertTrue(foundUserTicket.isPresent());
        foundUserTicket.ifPresent(ut -> {
            assertEquals("1234567890", ut.getUserId());
            assertEquals("123456", ut.getLottery().getTicket());
        });
    }

    @Test
    @DisplayName("Find user ticket by user ID and ticket ID but there is no user ticket")
    void TestFindByUserIdAndTicketButNoUserTicket() {
        Optional<UserTicket> foundUserTicket = userTicketRepository.findByUserIdAndTicket("1234567890", "123456");

        assertFalse(foundUserTicket.isPresent());
    }

    @Test
    @DisplayName("Find user tickets by user ID and ticket ID but there is no ticket")
    void TestFindByUserIdAndTicketButNoTicket() {
        Lottery lottery = new Lottery("000000", 1L, 10.0);
        UserTicket userTicket = new UserTicket("1234567890", "123456", 1L, lottery);

        lotteryRepository.save(lottery);
        userTicketRepository.save(userTicket);

        Optional<UserTicket> foundUserTicket = userTicketRepository.findByUserIdAndTicket("1234567890", "123456");

        assertFalse(foundUserTicket.isPresent());
    }

    @Test
    @DisplayName("Find user tickets by user ID and ticket ID but there is no user")
    void TestFindByUserIdAndTicketButNoUser() {
        Lottery lottery = new Lottery("123456", 1L, 10.0);

        lotteryRepository.save(lottery);

        Optional<UserTicket> foundUserTicket = userTicketRepository.findByUserIdAndTicket("1234567890", "123456");

        assertFalse(foundUserTicket.isPresent());
    }
}