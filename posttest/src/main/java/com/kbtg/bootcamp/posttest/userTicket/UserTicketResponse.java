package com.kbtg.bootcamp.posttest.userTicket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserTicketResponse {
    private List<String> ticket;
}
