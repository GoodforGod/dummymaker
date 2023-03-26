package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.parameterized.GenTime;
import io.dummymaker.generator.Generator;
import java.sql.Time;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Time from 1970 to 3000 Year
 *
 * @author Anton Kurako (GoodforGod)
 * @see Time
 * @since 10.03.2019
 */
public final class TimeSqlGenerator implements Generator<Time> {

    private final LocalTimeGenerator localTimeGenerator;

    public TimeSqlGenerator() {
        this(GenTime.MIN, GenTime.MAX);
    }

    public TimeSqlGenerator(long from, long to) {
        this.localTimeGenerator = new LocalTimeGenerator(from, to);
    }

    @Override
    public @NotNull Time get() {
        return Time.valueOf(localTimeGenerator.get());
    }
}
