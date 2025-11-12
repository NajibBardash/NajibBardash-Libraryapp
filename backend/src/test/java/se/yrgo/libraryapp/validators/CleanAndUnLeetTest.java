package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;

class CleanAndUnLeetTest {

    @ParameterizedTest
    @NullSource
    void testIfResultIsNull(String input) {
        assertThat(Utils.cleanAndUnLeet(input)).isNull();
    }

    @ParameterizedTest
    @EmptySource
    void testIfResultIsEmpty(String input) {
        assertThat(Utils.cleanAndUnLeet(input)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "1337, leet",
                    "1337 5P34K, leet speak",
                    "E5C4P3, escape",
                    "ÅS4, åsa",
                    "n00b, noob",
                    "66 KING, bb king",
                    "835T, best",
                    "13456780, leasbtbo"
            })
    void testCorrectResult(String input, String expected) {
        assertThat(Utils.cleanAndUnLeet(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "1337, Leet",
                    "1337 5P34K, 1337 5P34K",
                    "ÅS4, ÅS4",
                    "n00b, n00b",
                    "66 KING, 6b king",
                    "835T, 8est",
                    "13456780, 13456780",
            })
    void testInCorrectResult(String input, String wrongOutput) {
        assertThat(Utils.cleanAndUnLeet(input)).isNotEqualTo(wrongOutput);
    }

}