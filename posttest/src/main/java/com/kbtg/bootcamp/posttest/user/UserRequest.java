package com.kbtg.bootcamp.posttest.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotNull @Size(min = 10, max = 10) String userId, String ticketId) {
}
