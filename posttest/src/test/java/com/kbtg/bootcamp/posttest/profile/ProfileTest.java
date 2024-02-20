package com.kbtg.bootcamp.posttest.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileTest {
    @Autowired
    ProfileRepository profileRepository;

    @Test
    @DisplayName("EXP03 : Connect with User_ticket")
    void shouldNotReturnANull() {
        List<Profile> all = null;
        all = profileRepository.findAll();
        assertThat(all).isNull();
    }
}