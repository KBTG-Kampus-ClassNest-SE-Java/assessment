package com.kbtg.bootcamp.posttest.dto;

public record GetLotteriesResponse(
        Iterable<String> tickets
) {
}
