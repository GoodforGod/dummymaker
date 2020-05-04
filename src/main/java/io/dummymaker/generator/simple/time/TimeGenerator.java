package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author GoodforGod
 * @see Time
 * @since 10.03.2019
 */
public class TimeGenerator implements ITimeGenerator<Time> {

    private final LocalTimeGenerator generator = new LocalTimeGenerator();

    @Override
    public @NotNull Time generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public @NotNull Time generate(final long minUnix, final long maxUnix) {
        return Time.valueOf(generator.generate(minUnix, maxUnix));
    }
}
