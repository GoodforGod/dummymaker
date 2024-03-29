package io.goodforgod.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDateTime from 1970 to 3000 (exclusive) year with seconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see LocalDateTime
 * @since 26.05.2017
 */
public final class LocalDateTimeGenerator implements Generator<LocalDateTime> {

    private static final Pattern PATTERN = Pattern.compile("datetime|stamp|timestamp|expired?", CASE_INSENSITIVE);

    private final long from;
    private final long to;

    public LocalDateTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public LocalDateTimeGenerator(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull LocalDateTime get() {
        long usedFrom = from;
        long usedTo = to;
        if (usedFrom < 0)
            usedFrom = 0;
        if (usedTo > GenTime.MAX)
            usedTo = GenTime.MAX;

        final long amount = (usedTo < usedFrom)
                ? usedFrom
                : RandomUtils.random(usedFrom, usedTo);

        return LocalDateTime.ofEpochSecond(
                amount,
                0,
                ZoneOffset.UTC);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(60)
                .build();
    }
}
