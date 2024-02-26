package com.kbtg.bootcamp.posttest.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProfileTest {
    @Autowired
    ProfileRepository profileRepository;


    @Test
    @DisplayName("get User_ticket")
    void shouldReturnAnEntity() {
        List<Profile> all = null;
        all = profileRepository.findAll();
        assertThat(all).isNotNull();
        assertThat(all).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Connect with User_ticket")
    void shouldNotReturnANull() {
        List<Profile> all = null;
        all = profileRepository.findAll();
        assertThat(all).isNotNull();
    }
}