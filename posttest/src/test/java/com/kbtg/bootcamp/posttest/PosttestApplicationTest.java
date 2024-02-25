package com.kbtg.bootcamp.posttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class PosttestApplicationTest {
    public static void main(String[] args) {
        SpringApplication.from(com.kbtg.bootcamp.posttest.PosttestApplicationTest::main).with(PosttestApplicationTest.class).run(args);
    }
}
