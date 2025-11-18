package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class RealNameTest {

    @Test
    void testIfNumberOfWordsFromFileIsCorrect() {
        assertThat(RealName.getNumberOfBadWords()).isEqualTo(8);
    }

    @ParameterizedTest
    @ValueSource(strings = {"shit", "McCrack", "bullshit", "dang", "daft", "dong", "smack", "heck"})
    void testIfAllWordsAreReadFromFile(String badWord) {
        assertThat(RealName.getBadWords().contains(badWord)).isTrue();
    }

    @ParameterizedTest
    @NullSource
    void testShouldReturnTrueGivenNull(String input) {
        assertThat(RealName.validate(input)).isTrue();
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void testShouldReturnTrueGivenEmpty(String input) {
        assertThat(RealName.validate(input)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"No Shit", "Craig McCrack", "More Bullshit", "Mark Dang", "Daft Punk", "Ding Dong", "Smack A lot", "Heck Heiner"})
    void testWordsThatShouldFail(String badWord) {
        assertThat(RealName.validate(badWord)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"No Shiet", "Craig McCrock", "Angus Young", "Kelly Smeck"})
    void testWordsThatShouldPass(String goodWord) {
        assertThat(RealName.validate(goodWord)).isTrue();
    }
}