package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.BadRequestException;
import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService(userRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(userService)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("User should be created with valid input")
    void testCreateUserWithValidInput() {
        UserRequest request = new UserRequest();
        request.setName("John Doe");

        User user = new User("John Doe");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(request);

        assertEquals(user.getName(), createdUser.getName());
    }

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setName("John Doe");

        User user = new User("John Doe");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(request);

        assertEquals(user.getName(), createdUser.getName());
    }

    @Test
    void testGetUserById() {
        Long userId = 123L;
        User user = new User("John Doe");
        user.setUser_id(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(userId);

        assertEquals(userId, retrievedUser.getUser_id());
    }

    @Test
    void testGetAllUser() {
        User user1 = new User("John Doe");
        User user2 = new User("Jane Smith");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUser();

        assertEquals(2, users.size());
    }
}
