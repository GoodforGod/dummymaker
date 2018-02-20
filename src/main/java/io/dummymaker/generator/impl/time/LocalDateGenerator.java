package io.dummymaker.generator.impl.time;

import io.dummymaker.generator.IGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LocalDateGenerator implements IGenerator<LocalDate> {

    private final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();

    @Override
    public LocalDate generate() {
        final LocalDateTime gen = generator.generate();
        return LocalDate.of(gen.getYear(), gen.getMonth(), gen.getDayOfMonth());
    }
}
