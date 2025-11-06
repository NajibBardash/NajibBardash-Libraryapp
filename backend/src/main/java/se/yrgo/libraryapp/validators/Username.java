package se.yrgo.libraryapp.validators;

import java.util.regex.Pattern;

/**
 * This validator checks that the username match our high standard for proper names.
 * 
 * I.e. no funny characters or only special characters, or whitespace and at least four and no more than 30 characters long.
 */
public final class Username {
    private static final Pattern regex = Pattern.compile("[@._a-zA-Z0-9]{4,30}");
    private static final Pattern onlySpecialCharactersRegex = Pattern.compile("[@._]{4,30}");

    private Username() {}

    /**
     * Validates if the given name is a valid username.
     * 
     * A username should be at least four and no more than 30 characters long and only
     * contain ASCII letters, numbers and the characters @, ., _ and -.
     * It should not contain any other letters, not even whitespace.
     * It should not only contain sppecial characters.
     * 
     * @param name the name to check
     * @return true if valid, false if not
     * 
     */
    public static boolean validate(String name) {
        return regex.matcher(name).matches() && !onlySpecialCharactersRegex.matcher(name).matches();
    }
}
