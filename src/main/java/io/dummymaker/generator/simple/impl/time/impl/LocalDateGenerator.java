package io.dummymaker.generator.simple.impl.time.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;

import java.time.LocalDate;

/**
 * Generates LocalDate objects with range from 1970 to 3000
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LocalDateGenerator implements ITimeGenerator<LocalDate> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public LocalDate generate() {
        return generate(0, GenTime.MAX);
    }

    @Override
    public LocalDate generate(final long from, final long to) {
        return generator.generate(from, to).toLocalDate();
    }
}
