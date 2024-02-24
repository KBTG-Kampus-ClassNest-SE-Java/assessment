package com.kbtg.bootcamp.posttest.features.date;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeProviderService {
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
