package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates version as string (like 1.1.0-SNAPSHOT) or without SNAPSHOT suffix
 *
 * @author Anton Kurako (GoodforGod)
 * @since 6.4.2020
 */
public class VersionGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("ver(sion)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return CollectionUtils.random(1, 9)
                + "."
                + CollectionUtils.random(1, 99)
                + "."
                + CollectionUtils.random(1, 99)
                + (ThreadLocalRandom.current().nextBoolean()
                        ? "-SNAPSHOT"
                        : "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
