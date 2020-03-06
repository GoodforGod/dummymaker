package io.dummymaker.generator.simple.time;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.ITimeGenerator;

import java.time.LocalDate;

/**
 * Generates LocalDate objects with range from 1970 to 3000
 *
 * @author GoodforGod
 * @see LocalDate
 * @since 21.02.2018
 */
public class LocalDateGenerator implements ITimeGenerator<LocalDate> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public LocalDate generate() {
        return generate(0, GenTime.MAX_UNIX);
    }

    @Override
    public LocalDate generate(final long minUnix, final long maxUnix) {
        return generator.generate(minUnix, maxUnix).toLocalDate();
    }
}
