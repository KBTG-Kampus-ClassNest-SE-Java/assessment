package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;


@Entity
@Table(name = "user_ticket")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "user_id", nullable = false, length = 10)
	private String userId;
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Lottery ticketId;
	public User() {
	}

	public User(Integer id, String userId, Lottery ticketId) {
		this.id = id;
		this.userId = userId;
		this.ticketId = ticketId;
	}

	public Integer getId() {
		return id;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Lottery getTicketId() {
		return ticketId;
	}

	public void setTicketId(Lottery ticketId) {
		this.ticketId = ticketId;
	}
}





