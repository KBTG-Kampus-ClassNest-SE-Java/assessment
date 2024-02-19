package com.kbtg.bootcamp.posttest.validator.lottery_id;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsLotteryIdValidatorTest {

    private final IsLotteryIdValidator validator = new IsLotteryIdValidator();

    private static Stream<Arguments> IsValid_ShouldReturnFalse_WhenRequestIsInValid_DataSet() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of("12345S"),
                Arguments.of("ABACZG")
        );
    }

    @ParameterizedTest
    @MethodSource("IsValid_ShouldReturnFalse_WhenRequestIsInValid_DataSet")
    public void IsValid_ShouldReturnFalse_WhenRequestIsInValid(String value) {
        final boolean isValid = validator.isValid(value, null);

        assertFalse(isValid);
    }

    private static Stream<Arguments> IsValid_ShouldReturnTrue_WhenRequestIsValid_DataSet() {
        return Stream.of(
                Arguments.of("120721"),
                Arguments.of("652167"),
                Arguments.of("123456")
        );
    }

    @ParameterizedTest
    @MethodSource("IsValid_ShouldReturnTrue_WhenRequestIsValid_DataSet")
    public void IsValid_ShouldReturnTrue_WhenRequestIsValid(String value) {
        final boolean isValid = validator.isValid(value, null);

        assertTrue(isValid);
    }

}