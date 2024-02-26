package com.kbtg.bootcamp.posttest.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbtg.bootcamp.posttest.core.entity.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Table(name = "lottery")
@Data
@NoArgsConstructor
public class Lottery extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Length(min = 6, max = 6, message = "Ticket must contain 6 characters")
    private String ticket;

    @Column(name = "price", precision = 6, scale = 2)
    @JsonIgnore
    @DecimalMin(value = "0.00", message = "Price has to be non negative number")
    private BigDecimal price;

    @JsonIgnore
    @Min(value = 1, message = "Amount must be at least 1")
    private Integer amount;

    @JsonIgnore
    @Column(name = "current_amount")
    @JsonProperty("current_amount")
    private Integer currentAmount;

    public Lottery(String ticket, BigDecimal price, Integer amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }
}
