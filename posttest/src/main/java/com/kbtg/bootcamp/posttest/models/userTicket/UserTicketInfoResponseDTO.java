package com.kbtg.bootcamp.posttest.models.userTicket;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserTicketInfoResponseDTO {
    private List<String> tickets;
    private Integer count;
    private Double cost;

    public UserTicketInfoResponseDTO(List<String> tickets, Integer count, Double cost) {
        this.tickets = tickets;
        this.count = count;
        this.cost = cost;
    }
}
