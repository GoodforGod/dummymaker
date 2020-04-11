package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates version as string (like 1.1.0-SNAPSHOT) or without SNAPSHOT suffix
 *
 * @author Anton Kurako (GoodforGod)
 * @since 6.4.2020
 */
public class VersionGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("ver(sion)?", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return ThreadLocalRandom.current().nextInt(0, 1)
                + "."
                + ThreadLocalRandom.current().nextInt(1, 9)
                + "."
                + ThreadLocalRandom.current().nextInt(1, 9)
                + (ThreadLocalRandom.current().nextBoolean() ? "-SNAPSHOT" : "");
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
