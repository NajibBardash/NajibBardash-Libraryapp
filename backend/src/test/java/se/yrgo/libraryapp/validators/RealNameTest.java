package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class RealNameTest {
    private static Logger logger = LoggerFactory.getLogger(RealName.class);
    private static final Set<String> invalidWords = new HashSet<>();

    @BeforeAll
    static void initAll() {
        logger.info("Initialising tests");
        try (InputStream is = RealName.class.getClassLoader().getResourceAsStream("bad_words.txt");
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            invalidWords.addAll(reader.lines().collect(Collectors.toSet()));
        } catch (IOException ex) {
            logger.error("Unable to initialize list of bad words", ex);
        }
    }

    @AfterAll
    static void tearDownAll() {
        logger.info("Tearing down all bad words");
        invalidWords.clear();
    }

    @ParameterizedTest
    @ValueSource(strings = {"afaf"})
    void testWordsThatShouldPass(String word) {
        assertThat(RealName.validate(word)).isTrue();
    }
}