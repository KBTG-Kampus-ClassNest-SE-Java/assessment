package com.kbtg.bootcamp.posttest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListAllTicketsResponseDTO {
    List<String> tickets;
}
