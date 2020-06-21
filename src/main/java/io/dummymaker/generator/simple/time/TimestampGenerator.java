package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

/**
 * Generates Sql Timestamp from 1970 to 3000 Year with nanoseconds precision
 *
 * @author GoodforGod
 * @see Timestamp
 * @since 21.02.2018
 */
public class TimestampGenerator implements ITimeGenerator<Timestamp> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public @NotNull Timestamp generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Timestamp generate(final long minUnix, final long maxUnix) {
        return Timestamp.valueOf(generator.generate(minUnix, maxUnix));
    }
}
