package com.kbtg.bootcamp.posttest.repositories;

import com.kbtg.bootcamp.posttest.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setupTestData() {
        user = new User("User 1");
        user.setUserId(1000000001);
    }

    @Test
    @DisplayName("Test find by id")
    public void findById() {
        userRepository.save(user);

        var existsUser = userRepository.findById(user.getUserId());

        assertThat(existsUser).isNotNull();
    }
}
