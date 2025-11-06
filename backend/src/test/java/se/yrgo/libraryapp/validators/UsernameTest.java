package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsernameTest {
    @ParameterizedTest
    @ValueSource(strings = {"bosse", "nostrodamus", "bertil", "test", "aaaa"})
    void testCorrectUsernameLength(String userName) {
        assertThat(Username.validate(userName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "aa", "a"})
    void testIncorrectUsernameLength(String userName) {
        assertThat(Username.validate(userName)).isFalse();
    }
}
