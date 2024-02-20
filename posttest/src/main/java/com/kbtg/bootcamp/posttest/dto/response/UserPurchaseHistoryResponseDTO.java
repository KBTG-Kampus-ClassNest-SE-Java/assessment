package com.kbtg.bootcamp.posttest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserPurchaseHistoryResponseDTO {
    Set<String> tickets;
    int count;
    int cost;
}
