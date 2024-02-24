package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {


    @Test
    @DisplayName("get user by ID should re turn single User")
    void getUserById() {
        User user = new User("test");
//        User testUser = userController.getUserById(user.getId());

        assertEquals(user.getName(), "test");
    }

    @Test
    @DisplayName("Name should be between 1 to 100 characters and only contain alphabets")
    void testCreateUserWithValidName() throws Exception {

    }

    @Test
    @DisplayName("Name should not be empty")
    void testCreateUserWithEmptyName() {
//        UserRequest request = mock(UserRequest.class);
//        assertThrows(NullPointerException.class, () -> userService.createUser(request));
    }

    @Test
    @DisplayName("Name should not exceed 100 characters")
    void testCreateUserWithExcessiveNameLength() {
//        String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris condimentum";
//        UserRequest request = new UserRequest(longName);
////        assertEquals(null, () -> userService.createUser(request));
//        User user = userService.createUser(request);
//        // Check if the returned value is null
//        assertNull(user);
//        assertThrows(BadRequestException.class, () -> userService.createUser(request));
    }


    @Test
    @DisplayName("User's lotteries should be deleted after selling")
    void deleteLotteryAfterSell() {

    }
}