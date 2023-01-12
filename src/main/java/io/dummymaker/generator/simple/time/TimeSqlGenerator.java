package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.TimeGenerator;
import java.sql.Time;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author Anton Kurako (GoodforGod)
 * @see Time
 * @since 10.03.2019
 */
public final class TimeSqlGenerator implements TimeGenerator<Time> {

    private static final LocalTimeGenerator GENERATOR = new LocalTimeGenerator();

    @Override
    public @NotNull Time get() {
        return get(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Time get(long fromUnixTime, long toUnixTime) {
        return Time.valueOf(GENERATOR.get(fromUnixTime, toUnixTime));
    }
}
