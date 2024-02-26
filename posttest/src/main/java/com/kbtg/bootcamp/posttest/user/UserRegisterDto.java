package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.NotNull;

public class UserRegisterDto {


    @NotNull
    public String username;

    @NotNull
    public String password;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
