package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains EMAIL service provides as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 30.05.2017
 */
public final class EmailServicesBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "zoho", "yandex", "mail", "gmail", "outlook", "proton", "aim", "icloud", "yahoo", "custom", "aol", "gmx", "hotmail",
            "inbox");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_ENGLISH;
    }
}
