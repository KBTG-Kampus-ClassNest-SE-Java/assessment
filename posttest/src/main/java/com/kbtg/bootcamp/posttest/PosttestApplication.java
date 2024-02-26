package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PosttestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosttestApplication.class, args);

    }

    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            userService.mockupUser();
        };
    }

}
