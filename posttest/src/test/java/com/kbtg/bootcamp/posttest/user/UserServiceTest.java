package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exeption.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    ;
    private UserService userService;

    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService();
        userService = new UserService(lotteryService);
    }

    @Test
    @DisplayName("User can buy only ticket that admin created")
    void couldBuyOnlyExistLottery() {
        String lotteryId = "11111";
        String userId = "0000000000";
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.buyLottery(userId, lotteryId));
        String expectedMessage = "Lottery ticket no. " + lotteryId + " sold out";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("User should be created before buying a lottery")
    void checkIfUserAlreadyCreated() {
        String lotteryId = "111111";
        String userId = "00";
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.buyLottery(userId, lotteryId));
        String expectedMessage = "User : " + userId + " doesn't exist";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("User's lotteries should be deleted after selling")
    void deleteLotteryAfterSell() {
        List<Lottery> soldLotteries = userService.deleteLottery("00000000000","111111");
//        userService.deleteLottery(user.getId(), user.getLotteries().toString());
        assertEquals("1", soldLotteries);
    }
}