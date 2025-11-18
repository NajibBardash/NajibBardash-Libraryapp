package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class RealNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"shit", "McCrack", "bullshit", "dang", "daft", "dong", "smack", "heck"})
    void testWordsThatShouldNotPass(String badWord) {
        assertThat(RealName.validate(badWord)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"afaf"})
    void testWordsThatShouldPass(String goodWord) {
        assertThat(RealName.validate(goodWord)).isTrue();
    }
}