package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String> {
    @Query(value = """
            UPDATE lottery
            SET amount = amount + :amount
            WHERE ticket = :ticketId
            RETURNING *
            """, nativeQuery = true)
    Lottery updateTicketAmountInStore(String ticketId, int amount);

    @Query("""
            FROM Lottery l 
            WHERE l.amount > 0
            """)
    List<Lottery> findByAmountGreaterThan0();
}
