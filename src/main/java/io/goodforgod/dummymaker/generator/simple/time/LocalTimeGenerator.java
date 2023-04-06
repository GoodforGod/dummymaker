package io.goodforgod.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
import java.time.LocalTime;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates localTime object with range from 00:00:00 to 24:00:00
 *
 * @author Anton Kurako (GoodforGod)
 * @see LocalTime
 * @since 21.02.2018
 */
public final class LocalTimeGenerator implements Generator<LocalTime> {

    private static final Pattern PATTERN = Pattern.compile("time", CASE_INSENSITIVE);

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public LocalTimeGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public LocalTimeGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull LocalTime get() {
        return localDateTimeGenerator.get().toLocalTime();
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
