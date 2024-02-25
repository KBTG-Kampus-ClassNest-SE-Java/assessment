package com.kbtg.bootcamp.posttest.response;

import java.util.List;

public class TicketsResponse {


	private List<String> tickets;
	
	 public TicketsResponse(List<String> tickets) {
	        this.tickets = tickets;
	    }

	    public List<String> getTickets() {
	        return tickets;
	    }

	    public void setTickets(List<String> tickets) {
	        this.tickets = tickets;
	    }
}
