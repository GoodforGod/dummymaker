package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import java.sql.Time;

import io.dummymaker.generator.UnixTimeGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author Anton Kurako (GoodforGod)
 * @see Time
 * @since 10.03.2019
 */
public final class TimeGenerator implements UnixTimeGenerator<Time> {

    private static final LocalTimeGenerator generator = new LocalTimeGenerator();

    @Override
    public @NotNull Time get() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Time generate(final long fromUnixTime, final long toUnixTime) {
        return Time.valueOf(generator.generate(fromUnixTime, toUnixTime));
    }
}
