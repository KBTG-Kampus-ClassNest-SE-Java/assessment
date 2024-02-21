package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z\\s]*$")
        @Size(min = 1, max = 20, message = "Wallet name should be between 3 and 20 character")
        String name) {


}
