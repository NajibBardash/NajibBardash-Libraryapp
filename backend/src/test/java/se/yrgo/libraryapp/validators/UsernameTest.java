package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
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

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @EmptySource
    void testEmptyOrBlankUsername(String userName) {
        assertThat(Username.validate(userName)).isFalse();
    }

    @ParameterizedTest
    @NullSource
    void testNullCharacterLength(String userName) {
        assertThat(userName).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a123", "@12Z", "berit56", "eva_soderberg89", "lars.svensson77"})
    void testCorrectCharactersInUsername(String userName) {
        assertThat(Username.validate(userName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"å1234", "!12Z", "berit-56", "eva_söderberg89"})
    void testIncorrectCharactersInUsername(String userName) {
        assertThat(Username.validate(userName)).isFalse();
    }
}
