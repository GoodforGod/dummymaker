package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.sql.Timestamp;

/**
 * Generates Sql Timestamp from 1970 to 3000 Year with nanoseconds precision
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class TimestampGenerator implements IGenerator<Timestamp> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public Timestamp generate() {
        return Timestamp.valueOf(generator.generate());
    }
}
