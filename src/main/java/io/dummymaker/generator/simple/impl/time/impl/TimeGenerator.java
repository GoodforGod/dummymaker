package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.sql.Time;

/**
 * Generates Sql Time from 1970 to 3000 Year
 * @see Time
 *
 * @author GoodforGod
 * @since 10.03.2019
 */
public class TimeGenerator implements ITimeGenerator<Time> {

    private final LocalTimeGenerator generator = new LocalTimeGenerator();

    @Override
    public Time generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public Time generate(final long from, final long to) {
        return Time.valueOf(generator.generate(from, to));
    }
}