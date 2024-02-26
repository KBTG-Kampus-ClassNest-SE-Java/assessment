package com.kbtg.bootcamp.posttest.userticket.repo;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTicketRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserTicketRepo userTicketRepo;

    @Test
    @Transactional
    void testFindByUserIdAndTicketId_ExistingUserIdAndTicketId_ReturnListUserTicket() {

        Lottery lottery = new Lottery("123456", 10, 100);
        UserTicket userTicket = new UserTicket("0123456789", lottery);
        entityManager.persist(userTicket);
        entityManager.flush();

        List<UserTicket> found = userTicketRepo.findByUserIdAndTicketId("0123456789","123456");

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());

    }

    @Test
    @Transactional
    void testFindByUserIdAndTicketId_NonExistingUserIdOrTicketId_ReturnsEmptyList() {

        Lottery lottery = new Lottery("123456", 10, 100);
        UserTicket userTicket = new UserTicket("0123456789", lottery);
        entityManager.persist(userTicket);
        entityManager.flush();

        List<UserTicket> found1 = userTicketRepo.findByUserIdAndTicketId("","123456");
        List<UserTicket> found2 = userTicketRepo.findByUserIdAndTicketId("0123456789","");
        List<UserTicket> found3 = userTicketRepo.findByUserIdAndTicketId("","");

        assertTrue(found1.isEmpty());
        assertTrue(found2.isEmpty());
        assertTrue(found3.isEmpty());
        assertEquals(0, found1.size());
        assertEquals(0, found2.size());
        assertEquals(0, found3.size());
    }

    @Test
    @Transactional
    void testFindByUserId_ExistingUserId_ReturnListUserTicket() {

        Lottery lottery = new Lottery("123456", 10, 100);
        UserTicket userTicket = new UserTicket("0123456789", lottery);
        entityManager.persist(userTicket);
        entityManager.flush();

        List<UserTicket> found = userTicketRepo.findByUserId("0123456789");

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
    }

    @Test
    @Transactional
    void testFindByUserId_NonExistingUserId_ReturnsEmptyList() {

        Lottery lottery = new Lottery("123456", 10, 100);
        UserTicket userTicket = new UserTicket("0123456789", lottery);
        entityManager.persist(userTicket);
        entityManager.flush();

        List<UserTicket> found = userTicketRepo.findByUserId("");

        assertTrue(found.isEmpty());
        assertEquals(0, found.size());
    }
} 