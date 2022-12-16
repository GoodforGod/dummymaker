package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;
import java.sql.Timestamp;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Timestamp from 1970 to 3000 Year with nanoseconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Timestamp
 * @since 21.02.2018
 */
public final class TimestampGenerator implements TimeGenerator<Timestamp> {

    private static final LocalDateTimeGenerator GENERATOR = new LocalDateTimeGenerator();

    @Override
    public @NotNull Timestamp get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Timestamp get(long fromUnixTime, long toUnixTime) {
        return Timestamp.valueOf(GENERATOR.get(fromUnixTime, toUnixTime));
    }
}
