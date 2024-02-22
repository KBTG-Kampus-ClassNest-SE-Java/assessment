package com.kbtg.bootcamp.posttest.UserTicket;

import java.util.List;

public record UserTicketResponseDto(List<String> tickets, Long count, Double total) {
}
