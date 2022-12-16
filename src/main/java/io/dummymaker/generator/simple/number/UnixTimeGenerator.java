package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;

import java.util.regex.Pattern;

import io.dummymaker.generator.TimeGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Unix Time as long
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class UnixTimeGenerator implements TimeGenerator<Long> {

    private static final Pattern PATTERN = Pattern.compile("modif(y|ied)?|unix(time)?|epoch", CASE_INSENSITIVE);

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public @NotNull Long get() {
        return RandomUtils.random(GenTime.MIN_UNIX, GenTime.MAX_UNIX);
    }

    @Override
    public Long get(long fromUnixTime, long toUnixTime) {
        return RandomUtils.random(GenTime.MIN_UNIX, GenTime.MAX_UNIX);
    }
}
