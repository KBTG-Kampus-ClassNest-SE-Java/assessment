package com.kbtg.bootcamp.posttest.models.lottery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketIdResponseDTO {
    private String ticket;

    public TicketIdResponseDTO(String ticket) {
        this.ticket = ticket;
    }

}
