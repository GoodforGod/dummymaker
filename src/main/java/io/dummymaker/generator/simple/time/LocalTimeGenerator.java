package io.dummymaker.generator.simple.time;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.UnixTimeGenerator;
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
public final class LocalTimeGenerator implements UnixTimeGenerator<LocalTime> {

    private static final Pattern PATTERN = Pattern.compile("time", CASE_INSENSITIVE);

    private static final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull LocalTime get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull LocalTime generate(final long fromUnixTime, final long toUnixTime) {
        return generator.generate(fromUnixTime, toUnixTime).toLocalTime();
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
