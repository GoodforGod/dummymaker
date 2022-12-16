package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see LocalDateTime
 * @since 26.05.2017
 */
public final class LocalDateTimeGenerator implements TimeGenerator<LocalDateTime> {

    private static final Pattern PATTERN = Pattern.compile("datetime|stamp|timestamp|expired?", CASE_INSENSITIVE);

    @Override
    public @NotNull LocalDateTime get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull LocalDateTime get(long fromUnixTime, long toUnixTime) {
        long usedFrom = fromUnixTime;
        long usedTo = toUnixTime;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX_UNIX)
            usedTo = GenTime.MAX_UNIX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : RandomUtils.random(usedFrom, usedTo);

        return LocalDateTime.ofEpochSecond(
                amount,
                0,
                ZoneOffset.UTC);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -60;
    }
}
