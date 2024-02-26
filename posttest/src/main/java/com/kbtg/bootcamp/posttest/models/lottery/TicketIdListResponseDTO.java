package com.kbtg.bootcamp.posttest.models.lottery;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketIdListResponseDTO {
    private List<String> tickets = new ArrayList<>();

    public TicketIdListResponseDTO(List<String> tickets) {
        this.tickets = tickets;
    }
}
