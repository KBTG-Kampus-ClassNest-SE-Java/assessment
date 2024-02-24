package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String> {
    @Query(value = """
            UPDATE lottery
            SET amount = amount + :amount
            WHERE ticket = :ticketId
            RETURNING *
            """, nativeQuery = true)
    Lottery updateTicketAmountInStore(String ticketId, int amount);
}
