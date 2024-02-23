package com.kbtg.bootcamp.posttest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTicketResponseDto {
    private List<String> tickets;
    private Integer count;
    private Integer totalPrice;
}
