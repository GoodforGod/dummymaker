package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
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
public final class LocalDateGenerator implements UnixTimeGenerator<LocalDate> {

    private static final Pattern PATTERN = Pattern.compile("birth(date)?|date", CASE_INSENSITIVE);

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull LocalDate get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull LocalDate generate(final long fromUnixTime, final long toUnixTime) {
        return generator.generate(fromUnixTime, toUnixTime).toLocalDate();
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
