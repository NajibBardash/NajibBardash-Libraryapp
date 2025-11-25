package se.yrgo.libraryapp.validators;

import java.util.Locale;
import java.util.Map;

/**
 * Utility functions for validation classes.
 */
class Utils {
    private Utils() {
    }

    /**
     * Remove any non-alphabetic letters from a string, but keep any whitespace.
     * If a string is empty or blank - also after cleanup, return an empty string.
     * If the value is null, return null without throwing an exception.
     * A name is not mandatory to enter, thus null and empty values should be valid
     * Will return the string as all lowercase of the user's locale.
     *
     * @param str the string to filter
     * @return a string with all non-letters removed (except whitespace)
     */
    static String onlyLettersAndWhitespace(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        if (str.isBlank()) return "";

        String name = str
                .toLowerCase(Locale.ROOT)
                .codePoints()
                .filter(cp -> Character.isLetter(cp) || Character.isWhitespace(cp))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();

        return name.isBlank() ? "" : name;
    }

    /**
     * Converts any "leet speak" letters into their alphabetic equivalent (i.e. 4 to a etc.) and
     * then removes any letters that are not alphabetic (but not whitespace). Will return the string
     * as all lowercase.
     *
     * Null and empty values will be returned as either respectively.
     *
     * @param str the string to clean
     * @return a string without non-alphabetic characters (except whitespace)
     */
    static String cleanAndUnLeet(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        final var leetMap = Map.of(
                '1', 'l',
                '3', 'e',
                '4', 'a',
                '5', 's',
                '6', 'b',
                '7', 't',
                '8', 'b',
                '0', 'o');

        final StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            char newChar = leetMap.getOrDefault(ch, ch);
            res.append(newChar);
        }

        return onlyLettersAndWhitespace(res.toString());
    }
}
