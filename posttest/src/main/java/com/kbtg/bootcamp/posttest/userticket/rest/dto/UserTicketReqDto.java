package com.kbtg.bootcamp.posttest.userticket.rest.dto;

public class UserTicketReqDto {
    private Integer id;

    public UserTicketReqDto() {

    }

    public UserTicketReqDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
