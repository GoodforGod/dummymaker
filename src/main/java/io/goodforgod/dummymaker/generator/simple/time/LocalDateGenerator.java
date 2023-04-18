package io.goodforgod.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.Generator;
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
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(50)
                .build();
    }
}
