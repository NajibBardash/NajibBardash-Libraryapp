package se.yrgo.integrations.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public final class CustomConditions {
    private CustomConditions() {}

    public static ExpectedCondition<Boolean> elementHasBeenClicked(final WebElement element) {
        return ignoringAllExceptions(() -> element.click());
    }

    private static ExpectedCondition<Boolean> ignoringAllExceptions(final Runnable func) {
        return driver -> {
            try {
                func.run();
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        };
    }
}

