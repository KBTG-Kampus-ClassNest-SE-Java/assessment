package com.kbtg.bootcamp.posttest.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LotteryRequestDto {

    @NotBlank(message = "Ticket ID cannot be blank")
    @Pattern(regexp = "\\d{6}", message = "Value must be a 6-digit number")
    String ticket;
    @NotNull(message = "Price can't' be null") @Min(value = 1, message = "Price must be at least 1")
    double price;
    @NotNull(message = "Amount can't be null") @Min(value = 1, message = "Amount must be at least 1")
    int amount;

    public LotteryRequestDto(){

    }

    public LotteryRequestDto(String ticket, double price, int amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
