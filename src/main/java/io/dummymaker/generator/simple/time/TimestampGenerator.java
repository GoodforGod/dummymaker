package io.dummymaker.generator.simple.time;

import io.dummymaker.generator.Generator;
import java.sql.Timestamp;
import org.jetbrains.annotations.NotNull;

/**
 * Generates Sql Timestamp from 1970 to 3000 Year with nanoseconds precision
 *
 * @author Anton Kurako (GoodforGod)
 * @see Timestamp
 * @since 21.02.2018
 */
public final class TimestampGenerator implements Generator<Timestamp> {

    private final LocalDateTimeGenerator localDateTimeGenerator;

    public TimestampGenerator(long from, long to) {
        this.localDateTimeGenerator = new LocalDateTimeGenerator(from, to);
    }

    @Override
    public @NotNull Timestamp get() {
        return Timestamp.valueOf(localDateTimeGenerator.get());
    }
}
