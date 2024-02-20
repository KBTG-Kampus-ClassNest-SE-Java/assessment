package com.kbtg.bootcamp.lottery.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticket_id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_sold_back_flag")
    private String isSoldBackFlag;

    @Column(name = "price_paid")
    private BigDecimal pricePaid;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Integer getTicketId() {
        return ticket_id;
    }

    public void setTicketId(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}