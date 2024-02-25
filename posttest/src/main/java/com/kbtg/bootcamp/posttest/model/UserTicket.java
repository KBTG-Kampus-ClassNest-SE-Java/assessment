package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long ID;

    @Column(name = "LotteryID",length = 10)
    private Long lotteryID;

    @Column(name = "UserID")
    private String userID;

    @Column(name = "TicketNumber",length = 6)
    private String ticketNumber;

    @Column(name = "TicketPrice")
    private  int ticketPrice;

    @Column(name = "DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateTime;

    public UserTicket() {

    }

    public UserTicket(String userID,Long lotteryID, String ticketNumber,int ticketPrice) {
        this.userID = userID;
        this.lotteryID = lotteryID;
        this.ticketNumber = ticketNumber;
        this.ticketPrice = ticketPrice;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Long getLotteryID() {
        return lotteryID;
    }

    public void setLotteryID(Long lotteryID) {
        this.lotteryID = lotteryID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
