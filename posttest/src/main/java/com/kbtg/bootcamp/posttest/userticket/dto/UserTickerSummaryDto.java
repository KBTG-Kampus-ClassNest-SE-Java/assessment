package com.kbtg.bootcamp.posttest.userticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTickerSummaryDto {

    private List<String> tickets;
    private Integer count;
    private Integer cost;
}
