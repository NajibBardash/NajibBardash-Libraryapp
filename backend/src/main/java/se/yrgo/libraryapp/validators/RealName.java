package se.yrgo.libraryapp.validators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This validator checks that the real names match our high standard for proper names. I.e. no bad
 * words.
 */
public final class RealName {
    private static Logger logger = LoggerFactory.getLogger(RealName.class);
    private static final Set<String> invalidWords = new HashSet<>();

    static {
        try (InputStream is = RealName.class.getClassLoader().getResourceAsStream("bad_words.txt");
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                invalidWords.add(line.toLowerCase());
            }
        } catch (IOException ex) {
            logger.error("Unable to initialize list of bad words", ex);
        }
    }

    private RealName() {}

    /**
     * Validates if the given name is a valid and proper name.
     * If null then the program doesn't crash.
     * If blank then no need for word-matching.
     * 
     * @param name the name to check
     * @return true if valid, false if not
     * 
     */
    public static boolean validate(String name) {
        String cleanName = Utils.cleanAndUnLeet(name);
        if (cleanName != null && !cleanName.isBlank()) {
            String[] words = cleanName.split("\\W+");
            for (String word : words) {
                if (invalidWords.contains(word)) {
                    return false;
                }
            }
        }
        return true;
    }
}
