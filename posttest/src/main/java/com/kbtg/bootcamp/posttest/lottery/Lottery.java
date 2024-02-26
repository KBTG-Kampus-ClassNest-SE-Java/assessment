package com.kbtg.bootcamp.posttest.lottery;
import com.kbtg.bootcamp.posttest.user.UserTicket;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer id;

    private String ticket;
    private Integer price;
    private Integer amount;

    @OneToMany(mappedBy="lottery")
    private List<UserTicket> userTicket;

    public Lottery() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
