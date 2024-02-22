package com.kbtg.bootcamp.posttest.userTicket;

public class UserService {
    private UserTicketRepository userTicketRepository;

    public UserService(UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;
    }
}
