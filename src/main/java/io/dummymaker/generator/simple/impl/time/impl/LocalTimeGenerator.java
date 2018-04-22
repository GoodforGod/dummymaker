package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.time.LocalTime;

/**
 * Generates localTime object with range from 00:00:00 to 24:00:00
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LocalTimeGenerator implements ITimeGenerator<LocalTime> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public LocalTime generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public LocalTime generate(final long from, final long to) {
        return generator.generate(from, to).toLocalTime();
    }
}
