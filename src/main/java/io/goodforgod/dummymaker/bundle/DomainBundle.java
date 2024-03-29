package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains various domain extensions as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public final class DomainBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            ".com", ".co", ".net", ".org", ".club", ".shop", ".ru", ".me", ".io", ".ca", ".mail", ".be", ".ch", ".cl", ".city",
            ".center", ".company", ".person", ".de", ".site", ".online", ".us", ".am", ".be", ".bio", ".blog", ".bz", ".cl",
            ".cz", ".ec", ".eu", ".es", ".fr", ".gg", ".gr", ".help", ".im", ".in", ".ink", ".is", ".life", ".pl", ".sg");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(".рф");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
