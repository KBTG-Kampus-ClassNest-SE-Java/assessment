package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations="classpath:application-test.properties")
public class LotteryTicketRepositoryTest {

    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Test
    @DisplayName("Saving a new lottery ticket")
    void testSaveLotteryTicket() {
        LotteryTicket ticket = new LotteryTicket();
        ticket.setTicket("123456");
        ticket.setPrice(1);
        ticket.setAmount(1);

        LotteryTicket savedTicket = lotteryTicketRepository.save(ticket);

        assertEquals(1, savedTicket.getId());
        assertEquals(ticket.getTicket(), savedTicket.getTicket());
        assertEquals(ticket.getPrice(), savedTicket.getPrice());
        assertEquals(ticket.getAmount(), savedTicket.getAmount());
    }
}
