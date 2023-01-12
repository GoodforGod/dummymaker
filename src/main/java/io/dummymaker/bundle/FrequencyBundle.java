package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class FrequencyBundle extends AbstractBundle {

    private static final List<String> BUNDLE = Arrays.asList(
            "SECONDLY",
            "MINUTELY",
            "HOURLY",
            "DAILY",
            "WEEKLY",
            "MONTHLY",
            "YEARLY");

    public FrequencyBundle() {
        super(BUNDLE);
    }
}
