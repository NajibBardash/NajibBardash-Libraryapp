package se.yrgo.libraryapp.validators;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UtilsPropertyBasedTest {

    @Property
    void onlyLettersAndWhitespace(@ForAll String text) {
        String handledText = Utils.onlyLettersAndWhitespace(text);

        handledText.codePoints().forEach(cp ->
                assertThat(Character.isLetter(cp) || Character.isWhitespace(cp)).isTrue()
        );
    }

    @Property
    boolean cleanAndUnLeet(@ForAll("leetStrings") String text) {
        Pattern regex = Pattern.compile("[01345678]");
        String cleanedText = Utils.cleanAndUnLeet(text);

        return !regex.matcher(cleanedText).matches();
    }

    @Provide
    Arbitrary<String> leetStrings() {
        return Arbitraries.strings().withChars("abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ1234567890 \t");
    }


}