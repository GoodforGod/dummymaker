package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import java.time.LocalDate;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDate objects with range from 1970 to 3000
 *
 * @author Anton Kurako (GoodforGod)
 * @see LocalDate
 * @since 21.02.2018
 */
public final class LocalDateGenerator implements Generator<LocalDate> {

    private static final Pattern PATTERN = Pattern.compile("birth(date)?|date", CASE_INSENSITIVE);

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public LocalDateGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public LocalDateGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull LocalDate get() {
        return localDateTimeGenerator.get().toLocalDate();
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
