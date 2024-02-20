package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "ticket_number", referencedColumnName = "ticketNumber")
    private Lottery lottery;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Users userId;

    @Column(name = "is_sold_back_flag")
    private String isSoldBackFlag;

    @Column(name = "price_paid")
    private BigDecimal pricePaid;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;


    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticket_id) {
        this.ticketId = ticket_id;
    }

    public String getIsSoldBackFlag() {
        return isSoldBackFlag;
    }

    public void setIsSoldBackFlag(String isSoldBackFlag) {
        this.isSoldBackFlag = isSoldBackFlag;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public void setLotteryNumber(String lotteryNumber) {
        this.lottery = new Lottery();
        this.lottery.setTicketNumber(lotteryNumber);
    }

    public String getLotteryNumber() {
       return this.lottery.getTicketNumber();
    }
    public void setUserId(String userId) {
        this.userId = new Users();
        this.userId.setUserId(userId);
    }

    public Users getUserId() {
        return userId;
    }

}