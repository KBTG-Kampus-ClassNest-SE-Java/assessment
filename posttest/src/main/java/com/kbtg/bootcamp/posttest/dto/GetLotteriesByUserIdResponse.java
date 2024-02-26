package com.kbtg.bootcamp.posttest.dto;

import java.util.List;

public record GetLotteriesByUserIdResponse(
        List<String> tickets,
        Integer count,
        Integer cost
) {
}
