package com.kbtg.bootcamp.posttest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketResponseDto {

    private List<String> tickets;

    private int count;

    private int cost;
}
