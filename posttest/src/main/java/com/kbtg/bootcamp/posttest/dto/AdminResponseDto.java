package com.kbtg.bootcamp.posttest.dto;

public class AdminResponseDto {
    private String ticket;

    public AdminResponseDto(){

    }

    public AdminResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }


}
