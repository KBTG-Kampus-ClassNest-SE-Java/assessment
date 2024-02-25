package com.kbtg.bootcamp.posttest.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("when perform on get: /users should return list of users")
    void testCreateUserWithValidName() throws Exception {
        User user = new User("tester");
        user.setUser_id(6230413920L);

        when(userService.getAllUser()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$[0].name", is("tester")))
                .andExpect(jsonPath("$[0].user_id", is(6230413920L)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When performing a GET request on /users/{id}, return specific user information")
    void testCreateUserWithEmptyName() throws Exception {

        mockMvc.perform(get("/users/123"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Name should not exceed 100 characters")
    void testCreateUserWithExcessiveNameLength() throws Exception {
        String testName = "a".repeat(100);
        UserRequest validRequest = new UserRequest();
        validRequest.setName(testName);

        // Perform a POST request with the valid UserRequest
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }
}


