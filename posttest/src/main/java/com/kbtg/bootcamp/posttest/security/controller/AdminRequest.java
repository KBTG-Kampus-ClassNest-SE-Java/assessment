package com.kbtg.bootcamp.posttest.security.controller;

public record AdminRequest(String ticket, Double price, Long amount) {
}
