package com.kbtg.bootcamp.posttest.dto;

public class UserTicketResponseDto {
    private String ticket;
    private  int id;
    public UserTicketResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public UserTicketResponseDto(int id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
