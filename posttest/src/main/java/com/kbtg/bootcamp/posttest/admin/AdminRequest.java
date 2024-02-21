package com.kbtg.bootcamp.posttest.admin;

import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record AdminRequest(@NonNull @Size(min = 6, max = 6)String ticket, Double price, Long amount) {
}
