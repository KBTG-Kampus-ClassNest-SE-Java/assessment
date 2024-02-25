package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.BadRequestException;
import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userService)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("User should be created with valid input")
    void testCreateUserWithValidInput() {
        UserRequest request = new UserRequest();
        request.setName("test");

        User expectedUser = new User(request.getName());
        when(userService.createUser(any(UserRequest.class))).thenReturn(expectedUser);

        User createdUser = userService.createUser(request);

        assertEquals(expectedUser, createdUser);
    }

    @Test
    @DisplayName("Exception should be thrown when call getUserById with invalid ID")
    void testGetUserById(){



    }
}
