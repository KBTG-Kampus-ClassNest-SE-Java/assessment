package com.kbtg.bootcamp.posttest.user;

import java.util.List;

public class UserResponse {
    private List<String> tickets;

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public UserResponse(List<String> tickets) {
        this.tickets = tickets;
    }
}
