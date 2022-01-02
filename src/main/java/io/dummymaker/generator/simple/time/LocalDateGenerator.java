package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.LocalDate;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates LocalDate objects with range from 1970 to 3000
 *
 * @author GoodforGod
 * @see LocalDate
 * @since 21.02.2018
 */
public class LocalDateGenerator implements ITimeGenerator<LocalDate> {

    private final Pattern pattern = Pattern.compile("birth(date)?|date", CASE_INSENSITIVE);

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull LocalDate generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull LocalDate generate(final long minUnix, final long maxUnix) {
        return generator.generate(minUnix, maxUnix).toLocalDate();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -60;
    }
}
