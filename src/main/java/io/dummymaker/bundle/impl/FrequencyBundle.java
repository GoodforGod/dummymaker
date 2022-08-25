package io.dummymaker.bundle.impl;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public class FrequencyBundle extends BasicBundle {

    public FrequencyBundle() {
        super("SECONDLY",
                "MINUTELY",
                "HOURLY",
                "DAILY",
                "WEEKLY",
                "MONTHLY",
                "YEARLY");
    }
}
