package com.kbtg.bootcamp.posttest.models.userTicket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTicketIdResponseDTO {
    private String id;


    public UserTicketIdResponseDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
