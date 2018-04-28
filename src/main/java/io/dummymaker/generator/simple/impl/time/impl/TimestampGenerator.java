package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.sql.Timestamp;

/**
 * Generates Sql Timestamp from 1970 to 3000 Year with nanoseconds precision
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class TimestampGenerator implements ITimeGenerator<Timestamp> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public Timestamp generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public Timestamp generate(final long from, final long to) {
        return Timestamp.valueOf(generator.generate(from, to));
    }
}
