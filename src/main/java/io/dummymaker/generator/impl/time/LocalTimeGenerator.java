package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Generates localTime object with range from 00:00:00 to 24:00:00
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LocalTimeGenerator implements IGenerator<LocalTime> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public LocalTime generate() {
        final LocalDateTime l = generator.generate();
        return LocalTime.of(l.getHour(), l.getMinute(), l.getSecond());
    }
}
