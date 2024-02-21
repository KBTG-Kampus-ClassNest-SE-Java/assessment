package com.kbtg.bootcamp.posttest.user;

import java.util.List;

public class UserResponseEXP02 {
    private List<String> tickets;

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public UserResponseEXP02(List<String> tickets) {
        this.tickets = tickets;
    }
}
