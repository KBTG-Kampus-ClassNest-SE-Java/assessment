package com.kbtg.bootcamp.posttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
@SpringBootTest
class PosttestApplicationTests {

  public static void main(String[] args) {
    SpringApplication.from(PosttestApplication::main).with(PosttestApplication.class).run(args);
  }

}
