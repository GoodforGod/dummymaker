package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import java.time.LocalTime;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates localTime object with range from 00:00:00 to 24:00:00
 *
 * @author GoodforGod
 * @see LocalTime
 * @since 21.02.2018
 */
public class LocalTimeGenerator implements ITimeGenerator<LocalTime> {

    private final Pattern pattern = Pattern.compile("time", CASE_INSENSITIVE);

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull LocalTime generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull LocalTime generate(final long minUnix, final long maxUnix) {
        return generator.generate(minUnix, maxUnix).toLocalTime();
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
