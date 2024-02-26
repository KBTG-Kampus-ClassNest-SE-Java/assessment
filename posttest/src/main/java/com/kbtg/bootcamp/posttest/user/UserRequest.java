package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    @Size(min = 3, max = 20, message = "user name should be between 3 and 20 character")
    private String name;

}
