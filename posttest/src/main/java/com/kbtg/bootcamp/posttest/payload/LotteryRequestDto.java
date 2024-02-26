package com.kbtg.bootcamp.posttest.payload;

import jakarta.validation.constraints.*;


public class LotteryRequestDto {

	@NotBlank(message = "Ticket ID cannot be blank")
	@Pattern(regexp = "\\d{6}", message = "Value must be a 6-digit number")
	String ticket;
	@NotNull(message = "Price cannot be null") @Min(value = 1, message = "Price must be at least 1")
	Integer price;
	@NotNull(message = "Amount cannot be null") @Min(value = 1, message = "Amount must be at least 1")
	Integer amount;
	public LotteryRequestDto(String ticket, Integer price, Integer amount) {
		this.ticket = ticket;
		this.price = price;
		this.amount = amount;
	}
	public LotteryRequestDto() {
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
