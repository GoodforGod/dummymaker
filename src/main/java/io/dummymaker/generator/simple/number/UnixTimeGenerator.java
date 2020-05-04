package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates Unix Time as long
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class UnixTimeGenerator implements IGenerator<Long> {

    private final Pattern pattern = Pattern.compile("modif(y|ied)?|unix(time)?|epoch", CASE_INSENSITIVE);

    @Override
    public Long generate() {
        return Instant.now().getEpochSecond() - ThreadLocalRandom.current().nextLong(99_000_000L);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
