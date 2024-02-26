package com.kbtg.bootcamp.posttest.request;

import jakarta.validation.constraints.NotBlank;


public class LotteryRequest {

    @NotBlank(message = "Wrong userId")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}