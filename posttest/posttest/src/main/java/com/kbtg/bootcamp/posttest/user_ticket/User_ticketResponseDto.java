package com.kbtg.bootcamp.posttest.user_ticket;

import java.util.List;

public record User_ticketResponseDto(List<String> tickets, Integer count, Float total) {
}
