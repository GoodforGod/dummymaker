package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds
 * precision
 *
 * @author GoodforGod
 * @see LocalDateTime
 * @since 26.05.2017
 */
public class LocalDateTimeGenerator implements ITimeGenerator<LocalDateTime> {

    private final Pattern pattern = Pattern.compile("stamp|timestamp|birth(date)?", CASE_INSENSITIVE);

    @Override
    public LocalDateTime generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public LocalDateTime generate(final long minUnix, final long maxUnix) {
        long usedFrom = minUnix;
        long usedTo = maxUnix;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX_UNIX)
            usedTo = GenTime.MAX_UNIX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : CollectionUtils.random(usedFrom, usedTo);

        return LocalDateTime.ofEpochSecond(
                amount,
                0,
                ZoneOffset.UTC);
    }

    @Override
    public @Nullable Pattern getPattern() {
        return pattern;
    }
}
