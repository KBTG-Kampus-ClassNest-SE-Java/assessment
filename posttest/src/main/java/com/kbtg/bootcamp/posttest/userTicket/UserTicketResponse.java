package com.kbtg.bootcamp.posttest.userTicket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTicketResponse {
    private String id;

    public UserTicketResponse(Long userId) {
    }
}
