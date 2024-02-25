package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.userTicket.UserTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Name should be between 1 to 100 characters and only contain alphabets")
    void testCreateUserWithValidName() {
        String testName = "tester";

    }

    @Test
    @DisplayName("Name should not be empty")
    void testCreateUserWithEmptyName() {

    }

    @Test
    @DisplayName("Name should not exceed 100 characters")
    void testCreateUserWithExcessiveNameLength() {

    }
}


