package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OnlyLettersAndWhitespaceTest {

    @ParameterizedTest
    @ValueSource(strings = {"båsse !?2124", "Chefen6 6 -!?", "1a", "12345a"})
    void testIfAllCharactersAreEitherLettersOrWhitespace(String input) {
        for (Character c : Utils.onlyLettersAndWhitespace(input).toCharArray()) {
            assertThat(Character.isLetter(c) || Character.isWhitespace(c)).isTrue();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"HARALD", "aLbAtRoSs", "EvA", "gR4 gEr"})
    void testIfResultIsInLowerCase(String input) {
        assertThat(Utils.onlyLettersAndWhitespace(input)).isLowerCase();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "   ", "1 2 3 4 5", "!     ?"})
    @EmptySource
    void testIfResultIsEmptyOrWithWhitespace(String input) {
        assertThat(Utils.onlyLettersAndWhitespace(input)).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    void testIfResultIsNull(String input) {
        assertThat(Utils.onlyLettersAndWhitespace(input)).isNull();
    }
}