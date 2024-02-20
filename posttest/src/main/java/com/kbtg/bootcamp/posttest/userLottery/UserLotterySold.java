package com.kbtg.bootcamp.posttest.userLottery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserLotterySold {
    private List<String> tickets;
}
