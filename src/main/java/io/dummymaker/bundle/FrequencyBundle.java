package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class FrequencyBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Secondly", "Minutely", "Hourly", "Daily", "Weekly", "Monthly", "Yearly");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Ежесекундно", "Ежеминутно", "Ежечасно", "Ежедневно", "Еженедельно", "Ежемесячно", "Каждый год");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
