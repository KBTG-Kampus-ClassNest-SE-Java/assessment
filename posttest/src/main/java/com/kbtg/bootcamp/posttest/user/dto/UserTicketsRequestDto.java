package com.kbtg.bootcamp.posttest.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTicketsRequestDto {
    @NotNull(message = "user id must not be null")
    @Min(value = 10, message = "user id should be 10")
    @Max(value = 10, message = "user id should be 10 characters")
    private Integer userId;

}
